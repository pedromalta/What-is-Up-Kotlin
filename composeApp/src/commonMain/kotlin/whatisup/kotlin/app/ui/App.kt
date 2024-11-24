package whatisup.kotlin.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.compose.KoinContext
import whatisup.kotlin.app.ui.navigation.Navigation
import whatisup.kotlin.app.ui.theme.RepoAppTheme

/**
 * UI entrypoint
 */
@Composable
fun App() {
    // start logger
    Napier.base(DebugAntilog())

    KoinContext {

        RepoAppTheme {

            val navController = rememberNavController()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Navigation(navController)
            }
        }
    }
}