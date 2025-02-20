package whatisup.kotlin.app.ui

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import whatisup.kotlin.app.di.DataModules
import whatisup.kotlin.app.ui.di.UIModules

fun MainViewController() = ComposeUIViewController(
    configure = {
        startKoin {
            printLogger(Level.INFO)
            modules(DataModules().modules + UIModules().modules)
        }
    }
) {
    App()
}