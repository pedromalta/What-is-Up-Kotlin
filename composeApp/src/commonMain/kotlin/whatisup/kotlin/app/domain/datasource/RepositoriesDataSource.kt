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
import whatisup.kotlin.app.domain.models.RepositoryModel

interface RepositoriesDataSource {

    companion object {
        const val UNKNOWN_TOTAL_ITEM_COUNT = -1
        const val PER_PAGE = 30
    }

    val repositoriesTotalCount: BehaviorObservable<Int>

    //Use set to avoid duplicates
    val repositoriesSubject: BehaviorObservable<Set<RepositoryModel>>
    val loadingState: BehaviorObservable<Boolean>

    fun fetchRepositories(page: Int)
}

class RepositoriesDataSourceImpl(
    private val db: LocalDB,
    private val api: GithubApi,
    private val scheduler: Scheduler,
) : RepositoriesDataSource, DisposableScope by DisposableScope() {

    companion object {
        private const val TAG = "RepositoriesDataSource"
    }

    private val _loadingState = BehaviorSubject(false).scope { it.onComplete() }
    override val loadingState: BehaviorObservable<Boolean> = _loadingState

    private val _repositoriesTotalCount =
        BehaviorSubject(RepositoriesDataSource.UNKNOWN_TOTAL_ITEM_COUNT).scope { it.onComplete() }
    override val repositoriesTotalCount: BehaviorObservable<Int> = _repositoriesTotalCount

    private val _repositoriesSubject = BehaviorSubject(emptySet<RepositoryModel>()).scope { it.onComplete() }
    override val repositoriesSubject: BehaviorObservable<Set<RepositoryModel>> = _repositoriesSubject

    override fun fetchRepositories(page: Int) {
        Napier.d(message = "fetchRepositories(page: $page)", tag = TAG)
        if (loadingState.value) {
            Napier.w(message = "Busy!!", tag = TAG)
            return
        }
        startLoading()

        merge(
            // Get local data
            singleFromCoroutine {
                db.getRepositories(page)
            },
            // Get api data
            singleFromCoroutine {
                val apiRepositories = api.searchRepositories(page = page)
                _repositoriesTotalCount.onNext(apiRepositories.totalCount)
                //Add to local DB
                db.addOrUpdateRepositories(apiRepositories.items)
                db.getRepositories(page)
            },
        )
            .subscribeOn(scheduler)
            .observeOn(scheduler)
            .subscribe(
                onNext = { repositories ->
                    // We add the updated list to the current set so we don't mess with the old positions, the set nature
                    // of the data structure will keep the positions and prevent duplicates
                    _repositoriesSubject.onNext(repositoriesSubject.value + repositories)
                },
                onError = { error ->
                    Napier.e(
                        throwable = error,
                        tag = TAG,
                        message = "Error on fetchRepositories"
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