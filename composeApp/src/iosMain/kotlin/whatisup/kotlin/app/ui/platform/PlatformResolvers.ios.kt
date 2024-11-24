package whatisup.kotlin.app.ui.platform

import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

/**
 * Current platform
 */
actual val currentPlatform: Platform
    get() = Platform.IOS