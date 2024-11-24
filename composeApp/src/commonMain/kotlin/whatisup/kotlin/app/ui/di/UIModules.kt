package whatisup.kotlin.app.ui.di

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.request.crossfade
import coil3.util.DebugLogger
import org.koin.core.module.Module
import org.koin.dsl.module
import whatisup.kotlin.app.ui.viewmodels.MainViewModel

expect val diskCacheModule: Module


/**
 * UI (ViewModels) modules
 */
class UIModules {
    val diskCache = diskCacheModule

    val mainViewModel = module {
        factory<MainViewModel> { MainViewModel(get(), get()) }
    }

    val modules = diskCache + mainViewModel


}