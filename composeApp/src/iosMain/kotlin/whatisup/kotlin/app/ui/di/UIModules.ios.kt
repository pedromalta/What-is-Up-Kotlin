package whatisup.kotlin.app.ui.di

import coil3.disk.DiskCache
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

actual val diskCacheModule: Module = module {
    single<DiskCache> {
        val paths = NSSearchPathForDirectoriesInDomains(
            NSCachesDirectory,
            NSUserDomainMask,
            true
        )

        val path = paths.first().toString().toPath()
        DiskCache.Builder()
            .directory(
                path
            )
            .build()
    }
}