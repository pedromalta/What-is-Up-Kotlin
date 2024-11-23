package whatisup.kotlin.app.ui

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin
import whatisup.kotlin.app.di.DataModules
import whatisup.kotlin.app.ui.di.UIModules

fun MainViewController() = ComposeUIViewController {
    startKoin {
        modules(DataModules().dataSource + UIModules().mainViewModel)
    }
    App()
}