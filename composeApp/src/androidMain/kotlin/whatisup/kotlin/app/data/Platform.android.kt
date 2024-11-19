package whatisup.kotlin.app.data

import android.os.Build
import whatisup.kotlin.app.domain.Platform

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()