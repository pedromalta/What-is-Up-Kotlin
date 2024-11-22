package whatisup.kotlin.app.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.badoo.reaktive.coroutinesinterop.asScheduler
import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.observable.flatMap
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.observable.subscribe
import com.badoo.reaktive.observable.subscribeOn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import whatisup.kotlin.app.domain.DataSource
import whatisup.kotlin.app.ui.mappers.RepoUIMapper
import whatisup.kotlin.app.ui.model.MainState

class MainViewModel(
    private val dataSource: DataSource,
): ViewModel(), DisposableScope by DisposableScope() {

    private val mapper = RepoUIMapper()

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    private val mainScheduler = Dispatchers.Main.asScheduler()
    private val ioScheduler = Dispatchers.IO.asScheduler()
    private val computationScheduler = Dispatchers.Default.asScheduler()

    init {
        fetchRepos(1)
    }

    val repoListObserver = dataSource.repoListSubject
        .subscribeOn(ioScheduler)
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

    fun fetchRepos(page: Int) {
        dataSource.fetchRepoList(page)
    }

    override fun onCleared() {
        dispose()
    }

}