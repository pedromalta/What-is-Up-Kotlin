package whatisup.kotlin.app.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import whatisup.kotlin.app.domain.datasource.RepositoriesDataSource
import whatisup.kotlin.app.ui.components.PagedLazyColumn
import whatisup.kotlin.app.ui.model.PullRequestsId
import whatisup.kotlin.app.ui.viewmodels.MainViewModel

typealias NavigateToPullRequestsDetailScreen = (pullRequestsId: PullRequestsId) -> Unit

@OptIn(ExperimentalSharedTransitionApi::class, KoinExperimentalAPI::class)
@Composable
fun SharedTransitionScope.RepositoriesScreen(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateToPullRequestsDetailScreen: NavigateToPullRequestsDetailScreen,
) {

    val viewModel = koinViewModel<MainViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val lazyListState = rememberLazyListState()

    val isPortrait = isPortrait()

    Scaffold { paddingValues ->

        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            PagedLazyColumn(
                modifier = modifier,
                lazyListState = lazyListState,
                items = state.repos,
                perPage = RepositoriesDataSource.PER_PAGE,
                isLoading = state.loadingRepoList,
                onLoadMoreItems = {
                    viewModel.fetchRepositories(state.currentPage + 1)
                },
                isLoadingContent = {
                    Column(
                        modifier = modifier.fillMaxSize().padding(8.dp),
                    ) {
                        CircularProgressIndicator(
                            modifier = modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            ) { repo ->
                RepoCardComponent(
                    modifier = modifier.fillMaxWidth(),
                    animatedVisibilityScope = animatedVisibilityScope,
                    repo = repo,
                    navigateToPullRequestsDetailScreen = navigateToPullRequestsDetailScreen
                )

            }

        }
    }
}

