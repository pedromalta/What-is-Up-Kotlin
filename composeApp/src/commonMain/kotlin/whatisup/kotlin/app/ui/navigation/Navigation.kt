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
import whatisup.kotlin.app.ui.RepositoriesScreen
import whatisup.kotlin.app.ui.PullRequestsDetailScreen
import whatisup.kotlin.app.ui.model.PullRequestsId

enum class NavigationScreens(val route: String) {
    HomeScreen("home_screen"),
    RepoDetailScreen("detail_screen")
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
            // RepositoriesScreen
            composable(
                route = NavigationScreens.HomeScreen.route
            ) {
                RepositoriesScreen(
                    animatedVisibilityScope = this,
                    navigateToPullRequestsDetailScreen = { pullRequestsId ->
                        navController.navigate(getDetailScreenRoute(pullRequestsId))
                    },
                )
            }

            // PullRequestsDetailScreen
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
                    if (owner == null || repo == null || repoId == 0L) return@composable
                    PullRequestsId(repoId, owner, repo)
                }
                PullRequestsDetailScreen(
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