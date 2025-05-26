package com.droiddevtips.admobnextgenads.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xCCA4C639),
    secondary = Color(0xFFE9F6BF),
    tertiary = Color(0x99A4C639),
    primaryContainer = Color(0xFF2A2A2A),
    surfaceContainer = Color(0xFF2A2A2A),
    background = Color(0xFF2A2A2A),
    inversePrimary = Color.Red,
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFA4C639),
    secondary = Color(0xFF769A04),
    tertiary = Color(0xFFD3E892),
    primaryContainer = Color.White,
    surfaceContainer = Color.White,
    background = Color.White

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun DroidDevTipsExamplesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}