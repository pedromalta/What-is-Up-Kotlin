package whatisup.kotlin.app.domain

import com.badoo.reaktive.coroutinesinterop.singleFromCoroutine
import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.scheduler.Scheduler
import com.badoo.reaktive.single.map
import com.badoo.reaktive.single.observeOn
import com.badoo.reaktive.single.subscribe
import com.badoo.reaktive.subject.behavior.BehaviorObservable
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import com.badoo.reaktive.subject.publish.PublishSubject
import whatisup.kotlin.app.data.api.services.GithubApi
import whatisup.kotlin.app.data.mappers.RepoApiPersistenceMapper
import whatisup.kotlin.app.data.mappers.RepoPersistenceModelMapper
import whatisup.kotlin.app.data.persistence.LocalDB
import whatisup.kotlin.app.domain.models.Repo
import whatisup.kotlin.app.domain.models.RepoPullRequest

interface DataSource {

    val repoListTotalCount: BehaviorObservable<Int>

    //Use set to avoid duplicates
    val repoListSubject: BehaviorObservable<Set<Repo>>
    val repoPullRequestSubject: Observable<RepoPullRequest>

    fun fetchRepoList(page: Int)
    fun fetchRepoPullRequest(owner: String, repo: String)
}

private const val UNKNOWN_TOTAL_ITEM_COUNT = -1

class DataSourceImpl(
    private val db: LocalDB,
    private val api: GithubApi,
    private val scheduler: Scheduler,
) : DataSource, DisposableScope by DisposableScope() {


    private val repoPersistenceModelMapper = RepoPersistenceModelMapper()

    private val _repoListTotalCount =
        BehaviorSubject(UNKNOWN_TOTAL_ITEM_COUNT).scope { it.onComplete() }
    override val repoListTotalCount: BehaviorObservable<Int> = _repoListTotalCount

    private val _repoListSubject = BehaviorSubject(emptySet<Repo>()).scope { it.onComplete() }
    override val repoListSubject: BehaviorObservable<Set<Repo>> = _repoListSubject

    private val _repoPullRequestSubject =
        PublishSubject<RepoPullRequest>().scope { it.onComplete() }
    override val repoPullRequestSubject: Observable<RepoPullRequest> = _repoPullRequestSubject


    init {
        singleFromCoroutine { db.getRepos(1) }
            .observeOn(scheduler)
            .map {
                it.map { origin -> repoPersistenceModelMapper.transform(origin) }
            }
            .subscribe(
                onSuccess = { result ->
                    _repoListSubject.onNext(repoListSubject.value + result)
                },
                onError = {

                }
            )
    }

    override fun fetchRepoList(page: Int) {
        //val ioScheduler = Dispatchers.IO.asScheduler()
        val repoApiPersistenceMapper = RepoApiPersistenceMapper(page)

        singleFromCoroutine { db.getRepos(page) }
            .observeOn(scheduler)
            .map {
                it.map { origin -> repoPersistenceModelMapper.transform(origin) }
            }
            .subscribe(
                onSuccess = { result ->
                    _repoListSubject.onNext(repoListSubject.value + result)
                },
                onError = {

                }
            )

        singleFromCoroutine { api.searchRepositories(page = page) }
            .observeOn(scheduler)
            .map {
                _repoListTotalCount.onNext(it.totalCount)
                it.items.map { origin -> repoApiPersistenceMapper.transform(origin) }
            }
            .map {
                db.addOrUpdateRepos(it)
                db.getRepos(page)
            }
            .map {
                it.map { origin -> repoPersistenceModelMapper.transform(origin) }
            }
            .subscribe(
                onSuccess = { result ->
                    _repoListSubject.onNext(repoListSubject.value + result)
                },
                onError = {

                }
            )

    }

    override fun fetchRepoPullRequest(owner: String, repo: String) {
        TODO("Not yet implemented")
    }

}