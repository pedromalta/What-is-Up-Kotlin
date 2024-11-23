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
import whatisup.kotlin.app.domain.datasource.RepositoriesDataSource
import whatisup.kotlin.app.domain.datasource.PullRequestsDataSource
import whatisup.kotlin.app.ui.mappers.RepositoryMapper
import whatisup.kotlin.app.ui.mappers.PullRequestMapper
import whatisup.kotlin.app.ui.model.MainState
import whatisup.kotlin.app.ui.model.PullRequests
import whatisup.kotlin.app.ui.model.PullRequestsId

class MainViewModel(
    private val repoListDataSource: RepositoriesDataSource,
    private val repoPullRequestsDataSource: PullRequestsDataSource,
): ViewModel(), DisposableScope by DisposableScope() {

    private val repoListMapper = RepositoryMapper()
    private val repoPullRequestsMapper = PullRequestMapper()

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        fetchRepos(1)
    }

    val repoListObserver = repoListDataSource.repositoriesSubject
        .subscribeOn(computationScheduler)
        .observeOn(computationScheduler)
        .map { repoList ->
            repoList.map { repoDomain ->
                repoListMapper.to(repoDomain)
            }
        }
        .observeOn(mainScheduler)
        .subscribe { repoListUi ->
            _state.value = _state.value.copy(
                repos = repoListUi,
                currentPage = repoListUi.size / RepositoriesDataSource.PER_PAGE,
            )
        }.scope()

    val loadingRepoListObserver = repoListDataSource.loadingState
        .subscribeOn(computationScheduler)
        .observeOn(mainScheduler)
        .subscribe { loadingState ->
            _state.value = _state.value.copy(
                loadingRepoList = loadingState,
            )
        }.scope()


    val repoPullRequestsObserver = repoPullRequestsDataSource.pullRequestsSubject
        .subscribeOn(computationScheduler)
        .observeOn(computationScheduler)
        .map { repoPullRequests ->
            val pullRequestListUi = repoPullRequests.pullRequests.map { repoDomain ->
                repoPullRequestsMapper.to(repoDomain)
            }
            PullRequests(
                id = repoPullRequests.repoId,
                repoId = repoPullRequests.repoId,
                userName = repoPullRequests.userName,
                repoName = repoPullRequests.repoName,
                pullRequests = pullRequestListUi,
            )
        }
        .observeOn(mainScheduler)
        .subscribe { pullRequests ->
            _state.value = _state.value.copy(
                pullRequests = pullRequests,
            )
        }.scope()

    val loadingPullRequestsObserver = repoPullRequestsDataSource.loadingState
        .subscribeOn(computationScheduler)
        .observeOn(mainScheduler)
        .subscribe { loadingState ->
            _state.value = _state.value.copy(
                loadingPullRequests = loadingState,
            )
        }.scope()

    fun fetchRepos(page: Int) {
        repoListDataSource.fetchRepositories(page)
    }

    fun fetchRepoPullRequests(pullRequestsId: PullRequestsId) {
        repoPullRequestsDataSource.fetchPullRequests(
            repoId = pullRequestsId.repoId,
            owner = pullRequestsId.owner,
            repo = pullRequestsId.repo,
        )
    }

    override fun onCleared() {
        dispose()
    }

}