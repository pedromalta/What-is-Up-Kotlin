package whatisup.kotlin.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.observable.subscribe
import com.badoo.reaktive.observable.subscribeOn
import com.badoo.reaktive.scheduler.computationScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import whatisup.kotlin.app.domain.datasource.DataSource
import whatisup.kotlin.app.ui.mappers.RepoUIMapper
import whatisup.kotlin.app.ui.model.MainState

class MainViewModel(
    private val dataSource: DataSource,
): ViewModel(), DisposableScope by DisposableScope() {

    private val mapper = RepoUIMapper()

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        fetchRepos(1)
    }

    val repoListObserver = dataSource.repoListSubject
        .subscribeOn(computationScheduler)
        .observeOn(computationScheduler)
        .map { repos ->
            repos.map { repoDomain ->
                mapper.transform(repoDomain)
            }
        }
        .observeOn(mainScheduler)
        .subscribe { repos ->
            _state.value = _state.value.copy(
                repos = repos,
                currentPage = repos.size / DataSource.PER_PAGE,
            )
        }.scope()

    val loadingObserver = dataSource.loadingState
        .subscribeOn(computationScheduler)
        .observeOn(mainScheduler)
        .subscribe { loadingState ->
            _state.value = _state.value.copy(
                loading = loadingState,
            )
        }.scope()

    fun fetchRepos(page: Int) {
        dataSource.fetchRepoList(page)
    }

    override fun onCleared() {
        dispose()
    }

}