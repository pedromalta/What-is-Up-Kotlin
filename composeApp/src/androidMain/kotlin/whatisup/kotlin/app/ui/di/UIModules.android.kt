package whatisup.kotlin.app.ui.di

import android.content.Context
import coil3.disk.DiskCache
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module

actual val diskCacheModule: Module = module {
    single<DiskCache> {
        val context: Context = get()
        val path = context.cacheDir.absolutePath.toPath()
        DiskCache.Builder().directory(path)
            .build()
    }

}
