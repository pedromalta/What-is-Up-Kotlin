package whatisup.kotlin.app.ui

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import whatisup.kotlin.app.di.DataModules
import whatisup.kotlin.app.ui.di.UIModules

private var koinStarted = false

// Control start of Koin so that it doesn't start twice
private fun initKoin() {
    if (!koinStarted) {
        koinStarted = true
        startKoin {
            printLogger(Level.INFO)
            modules(DataModules().modules + UIModules().modules)
        }
    }
}

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}