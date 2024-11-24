package whatisup.kotlin.app.ui.platform


enum class Platform {
    Android,
    IOS,
}

/**
 * Current platform
 */
expect val currentPlatform: Platform
