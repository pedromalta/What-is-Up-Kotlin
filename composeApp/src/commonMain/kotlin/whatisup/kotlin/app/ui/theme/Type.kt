package whatisup.kotlin.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import whatisupkotlin.composeapp.generated.resources.Res
import whatisupkotlin.composeapp.generated.resources.solway_bold
import whatisupkotlin.composeapp.generated.resources.solway_extra_bold
import whatisupkotlin.composeapp.generated.resources.solway_light
import whatisupkotlin.composeapp.generated.resources.solway_medium
import whatisupkotlin.composeapp.generated.resources.solway_regular

@Composable
fun defaultFontFamily() = FontFamily(
    Font(Res.font.solway_light, weight = FontWeight.Light),
    Font(Res.font.solway_regular, weight = FontWeight.Normal),
    Font(Res.font.solway_medium, weight = FontWeight.Medium),
    Font(Res.font.solway_extra_bold, weight = FontWeight.ExtraBold),
    Font(Res.font.solway_bold, weight = FontWeight.Bold)
)

@Composable
fun AppTypography(): Typography {
    val defaultFontFamily = defaultFontFamily()
    val baseline = Typography()
    return Typography(
        displayLarge = baseline.displayLarge.copy(fontFamily = defaultFontFamily),
        displayMedium = baseline.displayMedium.copy(fontFamily = defaultFontFamily),
        displaySmall = baseline.displaySmall.copy(fontFamily = defaultFontFamily),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = defaultFontFamily),
        headlineMedium = baseline.headlineMedium.copy(fontFamily = defaultFontFamily),
        headlineSmall = baseline.headlineSmall.copy(fontFamily = defaultFontFamily),
        titleLarge = baseline.titleLarge.copy(fontFamily = defaultFontFamily),
        titleMedium = baseline.titleMedium.copy(fontFamily = defaultFontFamily),
        titleSmall = baseline.titleSmall.copy(fontFamily = defaultFontFamily),
        bodyLarge = baseline.bodyLarge.copy(fontFamily = defaultFontFamily),
        bodyMedium = baseline.bodyMedium.copy(fontFamily = defaultFontFamily),
        bodySmall = baseline.bodySmall.copy(fontFamily = defaultFontFamily),
        labelLarge = baseline.labelLarge.copy(fontFamily = defaultFontFamily),
        labelMedium = baseline.labelMedium.copy(fontFamily = defaultFontFamily),
        labelSmall = baseline.labelSmall.copy(fontFamily = defaultFontFamily),
    )
}


