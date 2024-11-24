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
import whatisup.kotlin.app.domain.datasource.RepositoriesDataSource
import whatisup.kotlin.app.ui.components.AppTopBar
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
@OptIn(ExperimentalSharedTransitionApi::class)
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

    Scaffold(
        topBar = {
            if (isPortrait) {
                AppTopBar(Res.string.app_name.resolve())
            }
        }
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
                    Column(
                        modifier = modifier.fillMaxSize().padding(8.dp),
                    ) {
                        CircularProgressIndicator(
                            modifier = modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            ) { repo ->
                RepositoryCardComponent(
                    modifier = modifier.fillMaxWidth(),
                    animatedVisibilityScope = animatedVisibilityScope,
                    repo = repo,
                    navigateToPullRequestsDetailScreen = navigateToPullRequestsDetailScreen
                )

            }

        }
    }
}

