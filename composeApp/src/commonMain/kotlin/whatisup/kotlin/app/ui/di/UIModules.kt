package whatisup.kotlin.app.ui.di

import com.badoo.reaktive.coroutinesinterop.asScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module
import whatisup.kotlin.app.ui.viewmodels.MainViewModel

class UIModules {
    val mainViewModel = module {
        factory<MainViewModel> { MainViewModel(get()) }
    }
}