package whatisup.kotlin.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import whatisup.kotlin.app.di.DataModules
import whatisup.kotlin.app.ui.di.UIModules

/**
 * Main application class
 */
class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            printLogger(Level.INFO)
            // inject Android context
            androidContext(this@MainApplication)
            modules(DataModules().dataSource + UIModules().mainViewModel)
        }

    }
}