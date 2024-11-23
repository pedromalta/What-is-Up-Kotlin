package whatisup.kotlin.app.domain.datasource

import com.badoo.reaktive.coroutinesinterop.singleFromCoroutine
import com.badoo.reaktive.disposable.scope.DisposableScope
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

interface PullRequestsDataSource {

    //Use set to avoid duplicates
    val pullRequestsSubject: BehaviorObservable<PullRequestsModel>
    val loadingState: BehaviorObservable<Boolean>

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
    override val pullRequestsSubject: BehaviorObservable<PullRequestsModel> = _pullRequestSubject

    override fun fetchPullRequests(repoId: Long, owner: String, repo: String) {
        Napier.d(message = "fetchPullRequests(repoId: $repoId, owner: $owner, repo: $repo)", tag = TAG)
        if (loadingState.value) {
            Napier.w(message = "Busy!!", tag = TAG)
            return
        }
        startLoading()

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