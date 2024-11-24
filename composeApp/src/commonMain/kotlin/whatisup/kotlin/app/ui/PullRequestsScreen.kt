package whatisup.kotlin.app.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import whatisup.kotlin.app.ui.model.PullRequestsId
import whatisup.kotlin.app.ui.viewmodels.MainViewModel

/**
 * Detail view showing the list of Pull Requests
 */
@OptIn(ExperimentalSharedTransitionApi::class, KoinExperimentalAPI::class)
@Composable
fun SharedTransitionScope.PullRequestsScreen(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    animatedVisibilityScope: AnimatedVisibilityScope,
    pullRequestsId: PullRequestsId,
    navigateBack: () -> Unit
) {

    val viewModel = koinViewModel<MainViewModel>()
    viewModel.fetchPullRequests(pullRequestsId)

    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold { paddingValues ->

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
                        Column(
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = pullRequest.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(
                                    top = 8.dp,
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 4.dp
                                )
                            )
                            Text(
                                text = pullRequest.body,
                                fontSize = 16.sp,
                                lineHeight = 16.sp,
                                maxLines = 6,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.padding(
                                    top = 4.dp,
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 4.dp
                                )
                            )
                            Text(
                                text = pullRequest.userName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.padding(
                                    top = 4.dp,
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 8.dp
                                )
                            )
                        }
                    }

                }
            }
        }
    }
}
