package whatisup.kotlin.app.domain.datasource

import com.badoo.reaktive.coroutinesinterop.singleFromCoroutine
import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.observable.doOnAfterFinally
import com.badoo.reaktive.observable.doOnBeforeSubscribe
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.observable.subscribe
import com.badoo.reaktive.observable.subscribeOn
import com.badoo.reaktive.scheduler.Scheduler
import com.badoo.reaktive.single.merge
import com.badoo.reaktive.subject.behavior.BehaviorObservable
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import io.github.aakira.napier.Napier
import whatisup.kotlin.app.data.api.services.GithubApi
import whatisup.kotlin.app.data.persistence.LocalDB
import whatisup.kotlin.app.domain.models.PullRequestsModel

/**
 * Data Source for Pull Requests
 * it contains a [pullRequestsObservable] source of truth
 * that will emit a [PullRequestsModel] whenever there's new data
 *
 * you can fetch new data using [fetchPullRequests]
 */
interface PullRequestsDataSource {

    /**
     * State of the loading process
     * indicates if the Data Source is working to fetch new Data
     */
    val loadingState: BehaviorObservable<Boolean>

    /**
     * Observable that emits a new [PullRequestsModel]
     */
    val pullRequestsObservable: BehaviorObservable<PullRequestsModel>

    /**
     * Fetch Pull Requests from a Repository
     * the results will be emitted by [pullRequestsObservable]
     * and while processing [loadingState] will be TRUE
     */
    fun fetchPullRequests(repoId: Long, owner: String, repo: String)
}

class PullRequestsDataSourceImpl(
    private val db: LocalDB,
    private val api: GithubApi,
    private val scheduler: Scheduler,
) : PullRequestsDataSource, DisposableScope by DisposableScope() {

    companion object {
        private const val TAG = "PullRequestsDataSource"
    }

    private val _loadingState = BehaviorSubject(false).scope { it.onComplete() }
    override val loadingState: BehaviorObservable<Boolean> = _loadingState

    private val _pullRequestSubject = BehaviorSubject(PullRequestsModel.EMPTY)
        .scope { it.onComplete() }
    override val pullRequestsObservable: BehaviorObservable<PullRequestsModel> = _pullRequestSubject

    override fun fetchPullRequests(repoId: Long, owner: String, repo: String) {
        Napier.d(
            message = "fetchPullRequests(repoId: $repoId, owner: $owner, repo: $repo)",
            tag = TAG
        )

        merge(
            // Get local data
            singleFromCoroutine {
                db.getPullRequests(repoId)
            },
            // Get api data
            singleFromCoroutine {
                val apiPullRequests = api.getRepoPullRequests(repoId, owner, repo)
                //Add to local DB
                db.addOrUpdatePullRequests(apiPullRequests)
                db.getPullRequests(repoId)
            },
        )
            .subscribeOn(scheduler)
            .observeOn(scheduler)
            .doOnBeforeSubscribe {
                startLoading()
            }
            .doOnAfterFinally {
                stopLoading()
            }
            .subscribe(
                onNext = { pullRequests ->
                    _pullRequestSubject.onNext(
                        PullRequestsModel(
                            repoId = repoId,
                            userName = owner,
                            repoName = repo,
                            pullRequests = pullRequests,
                        )
                    )
                },
                onError = { error ->
                    //TODO process errors in a more user-friendly way
                    //TODO recover errors that can be recovered
                    Napier.e(
                        throwable = error,
                        tag = TAG,
                        message = "Error on fetchPullRequests"
                    )
                },
                onComplete = {
                    stopLoading()
                },
            )
    }


    private fun startLoading() {
        _loadingState.onNext(true)
    }

    private fun stopLoading() {
        _loadingState.onNext(false)
    }

}