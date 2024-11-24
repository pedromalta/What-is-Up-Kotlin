package whatisup.kotlin.app.ui

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Indicates if the screen is being rendered on portrait mode
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun isPortrait(): Boolean {
    return calculateWindowSizeClass().widthSizeClass == WindowWidthSizeClass.Compact
}

/**
 * Resolve the string
 */
@Composable
fun StringResource.resolve(vararg args: Any): String = stringResource(this, *args)

/**
 * Default value for debouncing operations
 */
fun defaultDebounceInMillis() = 600L