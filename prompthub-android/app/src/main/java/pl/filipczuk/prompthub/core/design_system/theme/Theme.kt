package pl.filipczuk.prompthub.core.design_system.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Slate900,
    onPrimary = Color.White,
    secondary = Blue500,
    onSecondary = Color.White,
    tertiary = Orange600,
    onTertiary = Color.White,
    background = Color.White,
    surface = Color.White,
    onBackground = Slate900,
    onSurface = Slate900,
    error = Rose600,
    onError = Color.White,
    outline = Slate200,
    surfaceVariant = Slate50
)

@Composable
fun PromptHubTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
