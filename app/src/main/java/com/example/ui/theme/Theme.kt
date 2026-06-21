package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val OmniTierColorScheme = darkColorScheme(
    primary = CyberCyan,
    secondary = NeuralPurple,
    tertiary = CobaltBlue,
    background = TrueBlack,
    surface = ObsidianBlack,
    onPrimary = TrueBlack,
    onSecondary = TrueBlack,
    onTertiary = TrueBlack,
    onBackground = Slate100,
    onSurface = Slate100,
    surfaceVariant = SurfaceLight
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Force true black
    dynamicColor: Boolean = false, // Force custom palette
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = OmniTierColorScheme,
        typography = Typography,
        content = content
    )
}
