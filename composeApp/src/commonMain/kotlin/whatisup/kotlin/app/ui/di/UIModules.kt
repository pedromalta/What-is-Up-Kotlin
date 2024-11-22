package whatisup.kotlin.app.ui.di

import org.koin.dsl.module
import whatisup.kotlin.app.ui.viewmodels.MainViewModel

class UIModules {
    val mainViewModel = module {
        factory<MainViewModel> { MainViewModel(get(), get()) }
    }
}