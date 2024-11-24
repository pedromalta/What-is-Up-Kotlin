package whatisup.kotlin.app.ui.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import whatisup.kotlin.app.ui.PullRequestsScreen
import whatisup.kotlin.app.ui.RepositoriesScreen
import whatisup.kotlin.app.ui.model.PullRequestsId

/**
 * Available routes
 */
enum class NavigationScreens(val route: String) {
    HomeScreen("home_screen"),
    RepoDetailScreen("detail_screen")
}

/**
 * Main Master Detail View navigation host
 */
@Composable
fun Navigation(

    navController: NavController
) {
    val snackbarHostState = remember { SnackbarHostState() }
    NavHost(
        navController = navController as NavHostController,
        startDestination = NavigationScreens.HomeScreen.route
    ) {
        // RepositoriesScreen
        composable(
            route = NavigationScreens.HomeScreen.route
        ) {
            RepositoriesScreen(
                snackbarHostState = snackbarHostState,
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
            PullRequestsScreen(
                snackbarHostState = snackbarHostState,
                pullRequestsId = pullRequestsId,
                navigateBack = {
                    navController.popBackStack()
                }
            )

        }
    }
}

private fun getDetailScreenRoute(pullRequestsId: PullRequestsId): String {
    return with(pullRequestsId) {
        "${NavigationScreens.RepoDetailScreen.route}/$repoId/$owner/$repo"
    }
}