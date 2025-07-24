package com.droiddevtips.typography

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.droiddevtips.typography.typography.droidDevTipsTypography

/**
 * This is the custom Droid Dev Tips theme
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

val DarkColorScheme = darkColorScheme(
    primary = Color(0xCCA4C639),
    secondary = Color(0xFFE9F6BF),
    tertiary = Color(0x99A4C639),
    primaryContainer = Color(0xFF2A2A2A),
    secondaryContainer = Color(0xCCA4C639),
    surfaceContainer = Color(0xFF2A2A2A),
    background = Color(0xFF2A2A2A),
    inversePrimary = Color.White,
    surfaceContainerLow = Color(0xFFA4C639),
    surfaceContainerHighest = Color(0x99A4C639).copy(alpha = 0.30f),
    outline = Color(0xCCA4C639),
    onPrimary = Color.White
)

val LightColorScheme = lightColorScheme(
    primary = Color(0xFFA4C639),
    secondary = Color(0xFF769A04),
    tertiary = Color(0xFFD3E892),
    primaryContainer = Color.White,
    secondaryContainer = Color(0xCCA4C639),
    surfaceContainer = Color.White,
    background = Color.White,
    inversePrimary = Color.Black,
    surfaceContainerLow = Color(0xFFA4C639),
    surfaceContainerHighest = Color(0xFF769A04).copy(alpha = 0.30f),
    outline = Color(0xCCA4C639),
    onPrimary = Color.LightGray
)

@Composable
fun DroidDevTipsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = droidDevTipsTypography(isInDarkTheme = darkTheme),
        content = content
    )
}
