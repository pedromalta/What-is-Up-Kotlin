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
import whatisup.kotlin.app.domain.models.RepositoryModel

/**
 * Data Source for Repositories
 * it contains a [repositoriesObservable] source of truth
 * that will emit a set of [RepositoryModel] whenever there's new data
 *
 * you can fetch a new page of data using [fetchRepositories]
 */
interface RepositoriesDataSource {

    companion object {
        /**
         * Value that indicates we don't know how many items
         * there is on the repositories list
         */
        const val UNKNOWN_TOTAL_ITEM_COUNT = -1

        /**
         * Default number of repositories per page request to the Github API
         */
        const val PER_PAGE = 30
    }

    /**
     * State of the loading process
     * indicates if the Data Source is working to fetch new Data
     */
    val loadingState: BehaviorObservable<Boolean>

    /**
     * Emits the total count of repositories.
     */
    val repositoriesTotalCount: BehaviorObservable<Int>

    /**
     * Observable that emits a set of [RepositoryModel] it will append new data
     * and hopefully not duplicate old data
     */
    val repositoriesObservable: BehaviorObservable<Set<RepositoryModel>>

    /**
     * Fetch a page of Repositories
     * the results will be added to the current set of data and
     * emitted by [repositoriesObservable]
     * and while processing [loadingState] will be TRUE
     */
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

    private val _repositoriesSubject =
        BehaviorSubject(emptySet<RepositoryModel>()).scope { it.onComplete() }
    override val repositoriesObservable: BehaviorObservable<Set<RepositoryModel>> =
        _repositoriesSubject

    override fun fetchRepositories(page: Int) {
        Napier.d(message = "fetchRepositories(page: $page)", tag = TAG)

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
            .doOnBeforeSubscribe {
                startLoading()
            }
            .doOnAfterFinally {
                stopLoading()
            }
            .subscribe(
                onNext = { repositories ->
                    // We add the updated list to the current set so we don't mess with the old positions, the set nature
                    // of the data structure will keep the positions and prevent duplicates
                    _repositoriesSubject.onNext(repositoriesObservable.value + repositories)
                },
                onError = { error ->
                    //TODO process errors in a more user-friendly way
                    //TODO recover errors that can be recovered
                    Napier.e(
                        throwable = error,
                        tag = TAG,
                        message = "Error on fetchRepositories"
                    )
                    _repositoriesSubject.onError(error)
                }
            )
    }

    private fun startLoading() {
        _loadingState.onNext(true)
    }

    private fun stopLoading() {
        _loadingState.onNext(false)
    }

}