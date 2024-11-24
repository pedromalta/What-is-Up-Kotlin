package whatisup.kotlin.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import whatisup.kotlin.app.domain.datasource.RepositoriesDataSource
import whatisup.kotlin.app.ui.components.AppTopBar
import whatisup.kotlin.app.ui.components.LoadingRow
import whatisup.kotlin.app.ui.components.PagedLazyColumn
import whatisup.kotlin.app.ui.components.RepositoryCardComponent
import whatisup.kotlin.app.ui.model.PullRequestsId
import whatisup.kotlin.app.ui.viewmodels.MainViewModel
import whatisupkotlin.composeapp.generated.resources.Res
import whatisupkotlin.composeapp.generated.resources.app_name

typealias NavigateToPullRequestsDetailScreen = (pullRequestsId: PullRequestsId) -> Unit


/**
 * Master view containing a list of the repositories
 * use the [NavigateToPullRequestsDetailScreen] to navigate to the
 * detail view
 */
@Composable
fun RepositoriesScreen(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navigateToPullRequestsDetailScreen: NavigateToPullRequestsDetailScreen,
) {

    val viewModel = koinViewModel<MainViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(snackbarHostState) {
        snapshotFlow {
            state.errorMessage
        }.collect { errorMessage ->
            errorMessage?.apply {
                launch {
                    snackbarHostState.showSnackbar(
                        message = errorMessage,
                    )
                    viewModel.clearErrorMessage()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(Res.string.app_name.resolve())
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->

        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            PagedLazyColumn(
                modifier = modifier,
                lazyListState = lazyListState,
                items = state.repos.toList(),
                perPage = RepositoriesDataSource.PER_PAGE,
                isLoading = state.loadingRepoList,
                onLoadMoreItems = {
                    viewModel.fetchRepositories(state.currentPage + 1)
                },
                isLoadingContent = {
                    LoadingRow(modifier)
                }
            ) { repo ->
                RepositoryCardComponent(
                    modifier = modifier.fillMaxWidth(),
                    repo = repo,
                    navigateToPullRequestsDetailScreen = navigateToPullRequestsDetailScreen
                )

            }

        }
    }
}

