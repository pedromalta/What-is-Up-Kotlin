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
    private val repositoriesDataSource: RepositoriesDataSource,
    private val pullRequestsDataSource: PullRequestsDataSource,
): ViewModel(), DisposableScope by DisposableScope() {

    private val repositoriesMapper = RepositoryMapper()
    private val pullRequestsMapper = PullRequestMapper()

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        fetchRepositories(1)
    }

    val repositoriesObserver = repositoriesDataSource.repositoriesSubject
        .subscribeOn(computationScheduler)
        .observeOn(computationScheduler)
        .map { repositories ->
            repositories.map { repositoryDomain ->
                repositoriesMapper.transform(repositoryDomain)
            }
        }
        .observeOn(mainScheduler)
        .subscribe { repositoriesUi ->
            _state.value = _state.value.copy(
                repos = _state.value.repos + repositoriesUi,
                currentPage = (repositoriesUi.size / RepositoriesDataSource.PER_PAGE).coerceAtLeast(1),
            )
        }.scope()

    val loadingRepositoriesObserver = repositoriesDataSource.loadingState
        .subscribeOn(computationScheduler)
        .observeOn(mainScheduler)
        .subscribe { loadingState ->
            _state.value = _state.value.copy(
                loadingRepoList = loadingState,
            )
        }.scope()


    val pullRequestsObserver = pullRequestsDataSource.pullRequestsSubject
        .subscribeOn(computationScheduler)
        .observeOn(computationScheduler)
        .map { repoPullRequests ->
            val pullRequestListUi = repoPullRequests.pullRequests.map { repoDomain ->
                pullRequestsMapper.transform(repoDomain)
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

    val loadingPullRequestsObserver = pullRequestsDataSource.loadingState
        .subscribeOn(computationScheduler)
        .observeOn(mainScheduler)
        .subscribe { loadingState ->
            _state.value = _state.value.copy(
                loadingPullRequests = loadingState,
            )
        }.scope()

    fun fetchRepositories(page: Int) {
        repositoriesDataSource.fetchRepositories(page)
    }

    fun fetchPullRequests(pullRequestsId: PullRequestsId) {
        pullRequestsDataSource.fetchPullRequests(
            repoId = pullRequestsId.repoId,
            owner = pullRequestsId.owner,
            repo = pullRequestsId.repo,
        )
    }

    override fun onCleared() {
        dispose()
    }

}