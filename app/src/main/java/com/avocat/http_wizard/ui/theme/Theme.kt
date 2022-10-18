package com.avocat.http_wizard.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Foreground,
    secondary = Foreground,
    onPrimary = Background,
    onBackground = Foreground,
    onSecondary = Background,
    onSurface = Foreground,
    surface = Background,
)

@Composable
fun HEADWizardTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}