package whatisup.kotlin.app.domain

import com.badoo.reaktive.coroutinesinterop.singleFromCoroutine
import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.observable.subscribe
import com.badoo.reaktive.observable.subscribeOn
import com.badoo.reaktive.scheduler.Scheduler
import com.badoo.reaktive.single.merge
import com.badoo.reaktive.subject.behavior.BehaviorObservable
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import com.badoo.reaktive.subject.publish.PublishSubject
import io.github.aakira.napier.Napier
import whatisup.kotlin.app.data.api.services.GithubApi
import whatisup.kotlin.app.data.mappers.RepoApiPersistenceMapper
import whatisup.kotlin.app.data.mappers.RepoPersistenceModelMapper
import whatisup.kotlin.app.data.persistence.LocalDB
import whatisup.kotlin.app.domain.models.Repo
import whatisup.kotlin.app.domain.models.RepoPullRequest

interface DataSource {

    companion object {
        const val UNKNOWN_TOTAL_ITEM_COUNT = -1
        const val PER_PAGE = 30
    }

    val repoListTotalCount: BehaviorObservable<Int>

    //Use set to avoid duplicates
    val repoListSubject: BehaviorObservable<Set<Repo>>
    val repoPullRequestSubject: Observable<RepoPullRequest>

    fun fetchRepoList(page: Int)
    fun fetchRepoPullRequest(owner: String, repo: String)
}

class DataSourceImpl(
    private val db: LocalDB,
    private val api: GithubApi,
    private val scheduler: Scheduler,
) : DataSource, DisposableScope by DisposableScope() {

    companion object {
        private const val TAG = "DataSource"
    }

    private val repoPersistenceModelMapper = RepoPersistenceModelMapper()

    private val _repoListTotalCount =
        BehaviorSubject(DataSource.UNKNOWN_TOTAL_ITEM_COUNT).scope { it.onComplete() }
    override val repoListTotalCount: BehaviorObservable<Int> = _repoListTotalCount

    private val _repoListSubject = BehaviorSubject(emptySet<Repo>()).scope { it.onComplete() }
    override val repoListSubject: BehaviorObservable<Set<Repo>> = _repoListSubject

    private val _repoPullRequestSubject =
        PublishSubject<RepoPullRequest>().scope { it.onComplete() }
    override val repoPullRequestSubject: Observable<RepoPullRequest> = _repoPullRequestSubject

    override fun fetchRepoList(page: Int) {
        Napier.d(message = "fetchRepoList(page: $page)", tag = TAG)

        val repoApiPersistenceMapper = RepoApiPersistenceMapper(page)

        merge(
            // Get local data
            singleFromCoroutine { db.getRepos(page) },
            // Get api data
            singleFromCoroutine {
                val repoList = api.searchRepositories(page = page)
                _repoListTotalCount.onNext(repoList.totalCount)
                val persistenceList = repoList.items.map {
                    origin -> repoApiPersistenceMapper.transform(origin)
                }
                //Add to local DB
                db.addOrUpdateRepos(persistenceList)
                db.getRepos(page)
            },
        )
        .subscribeOn(scheduler)
        .observeOn(scheduler)
        .subscribe (
            onNext = { fullRepoList ->
                val updatedRepoList = fullRepoList.map { origin -> repoPersistenceModelMapper.transform(origin) }
                // We add the updated list to the current set so we don't mess with the old positions, the set nature
                // of the data structure will keep the positions and prevent duplicates
                _repoListSubject.onNext(repoListSubject.value + updatedRepoList)
            },
            onError = {

            }
        )
    }

    override fun fetchRepoPullRequest(owner: String, repo: String) {
        Napier.d(message = "fetchRepoPullRequest(owner: $owner, repo: $repo)", tag = TAG)
    }

}