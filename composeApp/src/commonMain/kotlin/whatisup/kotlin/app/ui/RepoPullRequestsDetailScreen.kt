package whatisup.kotlin.app.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import whatisup.kotlin.app.ui.model.PullRequestsId

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.RepoPullRequestsDetailScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    pullRequestsId: PullRequestsId,
    navigateBack: () -> Unit
) {

}