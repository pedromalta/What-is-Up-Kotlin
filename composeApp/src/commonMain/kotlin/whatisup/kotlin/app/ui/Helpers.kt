package whatisup.kotlin.app.ui

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable

/**
 * Indicates if the screen is being rendered on portrait mode
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun isPortrait(): Boolean {
    return calculateWindowSizeClass().widthSizeClass == WindowWidthSizeClass.Compact
}

/**
 * Default value for debouncing operations
 */
fun defaultDebounceInMillis() = 600L