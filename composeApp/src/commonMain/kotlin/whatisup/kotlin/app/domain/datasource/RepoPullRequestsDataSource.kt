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
import whatisup.kotlin.app.data.mappers.RepoPullRequestApiPersistenceMapper
import whatisup.kotlin.app.data.mappers.RepoPullRequestPersistenceModelMapper
import whatisup.kotlin.app.data.persistence.LocalDB
import whatisup.kotlin.app.domain.models.RepoPullRequests

interface RepoPullRequestsDataSource {

    //Use set to avoid duplicates
    val repoPullRequestSubject: BehaviorObservable<RepoPullRequests>
    val loadingState: BehaviorObservable<Boolean>

    fun fetchRepoPullRequest(repoId: Long, owner: String, repo: String)
}

class RepoPullRequestsDataSourceImpl(
    private val db: LocalDB,
    private val api: GithubApi,
    private val scheduler: Scheduler,
) : RepoPullRequestsDataSource, DisposableScope by DisposableScope() {

    companion object {
        private const val TAG = "RepoPullRequestsDataSource"
    }

    private val repoPullRequestPersistenceModelMapper = RepoPullRequestPersistenceModelMapper()

    private val _loadingState = BehaviorSubject(false).scope { it.onComplete() }
    override val loadingState: BehaviorObservable<Boolean> = _loadingState

    private val _repoPullRequestSubject = BehaviorSubject(RepoPullRequests.EMPTY)
        .scope { it.onComplete() }
    override val repoPullRequestSubject: BehaviorObservable<RepoPullRequests> = _repoPullRequestSubject

    override fun fetchRepoPullRequest(repoId: Long, owner: String, repo: String) {
        Napier.d(message = "fetchRepoPullRequest(owner: $owner, repo: $repo)", tag = TAG)
        if (loadingState.value) {
            Napier.w(message = "Busy!!", tag = TAG)
            return
        }
        startLoading()

        val repoApiPersistenceMapper = RepoPullRequestApiPersistenceMapper()

        merge(
            // Get local data
            singleFromCoroutine {
                db.getRepoPullRequests(repoId)
            },
            // Get api data
            singleFromCoroutine {
                val repoPullRequests = api.getRepoPullRequests(owner, repo)
                val persistenceRepoPullRequests = repoPullRequests.map { origin ->
                    repoApiPersistenceMapper.transform(origin)
                }
                //Add to local DB
                db.addOrUpdateRepoPullRequests(repoId, persistenceRepoPullRequests)
                persistenceRepoPullRequests
            },
        )
            .subscribeOn(scheduler)
            .observeOn(scheduler)
            .subscribe(
                onNext = { persistenceRepoPullRequests ->
                    val repoPullRequests = persistenceRepoPullRequests?.map {
                        origin -> repoPullRequestPersistenceModelMapper.transform(origin)
                    } ?: emptyList()
                    _repoPullRequestSubject.onNext(
                        RepoPullRequests(
                        repoId = repoId,
                        userName = owner,
                        repoName = repo,
                        pullRequests = repoPullRequests,
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