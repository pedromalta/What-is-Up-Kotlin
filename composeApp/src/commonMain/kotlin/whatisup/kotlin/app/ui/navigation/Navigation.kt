package whatisup.kotlin.app.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import whatisup.kotlin.app.ui.RepoListScreen
import whatisup.kotlin.app.ui.RepoPullRequestsDetailScreen
import whatisup.kotlin.app.ui.model.PullRequestsId

enum class NavigationScreens(val route: String) {
    HomeScreen("home_screen"),
    RepoDetailScreen("repo_detail_screen")
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Navigation(
    navController: NavController
) {
    SharedTransitionLayout {
        NavHost(
            navController = navController as NavHostController,
            startDestination = NavigationScreens.HomeScreen.route
        ) {
            // RepoListScreen
            composable(
                route = NavigationScreens.HomeScreen.route
            ) {
                RepoListScreen(
                    animatedVisibilityScope = this,
                    navigateToPullRequestsDetailScreen = { pullRequestsId ->
                        navController.navigate(getDetailScreenRoute(pullRequestsId))
                    },
                )
            }

            // RepoPullRequestsDetailScreen
            composable(
                route = "${NavigationScreens.RepoDetailScreen.route}/{repoId}/{owner}/{repo}",
                arguments = listOf(
                    navArgument("repoId") { type = NavType.LongType },
                    navArgument("owner") { type = NavType.StringType },
                    navArgument("repo") { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val arguments = backStackEntry.arguments ?: return@composable
                val pullRequestsId = arguments.let { args ->
                    val repoId = args.getLong("repoId")
                    val owner = args.getString("owner")
                    val repo = args.getString("repo")
                    if (owner == null || repo == null) return@composable
                    PullRequestsId(repoId, owner, repo)
                }
                RepoPullRequestsDetailScreen(
                    animatedVisibilityScope = this,
                    pullRequestsId = pullRequestsId,
                    navigateBack = {
                        navController.popBackStack()
                    }
                )

            }
        }
    }
}

private fun getDetailScreenRoute(pullRequestsId: PullRequestsId): String {
    return with(pullRequestsId) {
        "${NavigationScreens.RepoDetailScreen.route}/$repoId/$owner/$repo" }
}