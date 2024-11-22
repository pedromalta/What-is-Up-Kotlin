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
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import whatisup.kotlin.app.di.DataModules
import whatisup.kotlin.app.ui.di.UIModules
import whatisup.kotlin.app.ui.navigation.Navigation
import whatisup.kotlin.app.ui.theme.RepoAppTheme


@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            // Start Napier logger as soon as possible
            Napier.base(DebugAntilog())
            modules(DataModules().dataSource + UIModules().mainViewModel)
        }
    )
    {
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