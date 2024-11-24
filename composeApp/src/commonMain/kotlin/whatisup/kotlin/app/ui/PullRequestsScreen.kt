package whatisup.kotlin.app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sanitizer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import whatisup.kotlin.app.ui.components.AppTopBar
import whatisup.kotlin.app.ui.components.LoadingRow
import whatisup.kotlin.app.ui.components.PullRequestCardComponent
import whatisup.kotlin.app.ui.model.PullRequestsId
import whatisup.kotlin.app.ui.viewmodels.MainViewModel
import whatisupkotlin.composeapp.generated.resources.Res
import whatisupkotlin.composeapp.generated.resources.app_name
import whatisupkotlin.composeapp.generated.resources.empty_pull_requests_list

/**
 * Detail view showing the list of Pull Requests
 */
@Composable
fun PullRequestsScreen(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    pullRequestsId: PullRequestsId,
    navigateBack: () -> Unit
) {

    val uriHandler = LocalUriHandler.current
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

    viewModel.fetchPullRequests(pullRequestsId)

    Scaffold(
        topBar = {
            AppTopBar(
                title = Res.string.app_name.resolve(),
                navigateBack = navigateBack
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->

        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            state.pullRequests?.let { pullRequests ->

                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    state = lazyListState,
                ) {
                    items(
                        items = pullRequests.pullRequests,
                    ) { pullRequest ->

                        PullRequestCardComponent(
                            modifier = modifier
                                .fillMaxWidth()
                                .clickable {
                                    uriHandler.openUri(pullRequest.htmlUrl)
                                },
                            pullRequest = pullRequest
                        )
                    }

                    if (state.loadingPullRequests) {
                        item {
                            LoadingRow(modifier)
                        }
                    } else if (pullRequests.pullRequests.isEmpty()) {
                        item {
                            EmptyListCard()
                        }
                    }

                }
            }
        }
    }
}

@Composable
private fun EmptyListCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 100.dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Filled.Sanitizer,
            tint = MaterialTheme.colorScheme.error,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
        )
        Text(
            text = Res.string.empty_pull_requests_list.resolve(),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
