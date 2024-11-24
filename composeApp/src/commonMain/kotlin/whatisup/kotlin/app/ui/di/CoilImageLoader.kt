package whatisup.kotlin.app.ui.di

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.request.crossfade
import coil3.util.DebugLogger
import coil3.util.Logger

fun getCoilAsyncImageLoader(context: PlatformContext, diskCache: DiskCache) =
    ImageLoader.Builder(context)
        .crossfade(true)
        .diskCache(diskCache)
        .logger(DebugLogger().also {
            it.minLevel = Logger.Level.Info
        })
        .build()