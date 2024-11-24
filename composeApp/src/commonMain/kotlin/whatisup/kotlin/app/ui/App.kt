package whatisup.kotlin.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import whatisup.kotlin.app.ui.di.getCoilAsyncImageLoader
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
            // Start Coil ImageLoader
            val diskCache: DiskCache = koinInject()
            setSingletonImageLoaderFactory { context ->
                getCoilAsyncImageLoader(context, diskCache)
            }

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