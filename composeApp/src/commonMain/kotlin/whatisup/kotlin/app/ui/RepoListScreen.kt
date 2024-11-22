package whatisup.kotlin.app.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import whatisup.kotlin.app.domain.DataSource
import whatisup.kotlin.app.ui.components.PagedLazyColumn
import whatisup.kotlin.app.ui.model.PullRequestsId
import whatisup.kotlin.app.ui.viewmodels.MainViewModel

typealias NavigateToPullRequestsDetailScreen = (pullRequestsId: PullRequestsId) -> Unit

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.RepoListScreen(
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateToPullRequestsDetailScreen: NavigateToPullRequestsDetailScreen,
) {
    val viewModel = koinViewModel<MainViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()

    val lazyListState = rememberLazyListState()

    val isPortrait = isPortrait()

    Scaffold { paddingValues ->

        Text(
            "APP TITLE"
        )

        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            PagedLazyColumn(
                modifier = modifier.fillMaxSize(),
                lazyListState = lazyListState,
                items = state.value.repos,
                perPage = DataSource.PER_PAGE,
                isLoading = state.value.loading,
                onLoadMoreItems = {
                    viewModel.fetchRepos(state.value.currentPage + 1)
                },
                isLoadingContent = {
                    Text(
                        modifier = Modifier,
                        text =  "Loading...",
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                }
            ) { repo ->
                Text(
                    modifier = Modifier,
                    text = repo.name,
                    color = MaterialTheme.colorScheme.tertiary,
                )

            }

        }
    }
}

