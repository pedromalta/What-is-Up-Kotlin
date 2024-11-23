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
import whatisup.kotlin.app.data.mappers.api_to_persistence_model.PullRequestMapper as ApiToPersistenceMapper
import whatisup.kotlin.app.data.mappers.persistence_to_domain_model.PullRequestMapper as PersistenceToDomainMapper
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

    private val persistenceToDomainMapper = PersistenceToDomainMapper()

    private val _loadingState = BehaviorSubject(false).scope { it.onComplete() }
    override val loadingState: BehaviorObservable<Boolean> = _loadingState

    private val _pullRequestSubject = BehaviorSubject(PullRequestsModel.EMPTY)
        .scope { it.onComplete() }
    override val pullRequestsSubject: BehaviorObservable<PullRequestsModel> = _pullRequestSubject

    override fun fetchPullRequests(repoId: Long, owner: String, repo: String) {
        Napier.d(message = "fetchPullRequests(owner: $owner, repo: $repo)", tag = TAG)
        if (loadingState.value) {
            Napier.w(message = "Busy!!", tag = TAG)
            return
        }
        startLoading()

        val apiToPersistenceMapper = ApiToPersistenceMapper()

        merge(
            // Get local data
            singleFromCoroutine {
                db.getPullRequests(repoId)
            },
            // Get api data
            singleFromCoroutine {
                val pullRequests = api.getRepoPullRequests(owner, repo)
                val persistencePullRequests = pullRequests.map { origin ->
                    apiToPersistenceMapper.to(origin)
                }
                //Add to local DB
                db.addOrUpdatePullRequests(repoId, persistencePullRequests)
                persistencePullRequests
            },
        )
            .subscribeOn(scheduler)
            .observeOn(scheduler)
            .subscribe(
                onNext = { persistencePullRequests ->
                    val pullRequests = persistencePullRequests?.map {
                        origin -> persistenceToDomainMapper.to(origin)
                    } ?: emptyList()
                    _pullRequestSubject.onNext(
                        PullRequestsModel(
                        repoId = repoId,
                        userName = owner,
                        repoName = repo,
                        pullRequests = pullRequests,
                    )
                    )
                },
                onError = {

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