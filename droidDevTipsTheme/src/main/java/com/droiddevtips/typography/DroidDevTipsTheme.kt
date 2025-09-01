package com.droiddevtips.typography

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
    secondaryContainer = Color(0x99A4C639),
    surfaceContainer = Color(0xFF2A2A2A),
    background = Color(0xFF2A2A2A),
    onSecondaryContainer = Color.White,
    inversePrimary = Color.White,
    surfaceContainerLow = Color.White,
    surfaceContainerHighest = Color(0x99A4C639).copy(alpha = 0.30f),  // Color(0x99A4C639).copy(alpha = 0.30f)
    surfaceContainerHigh = Color(0xFF2A2A2A),
    outline = Color(0x99A4C639),
    outlineVariant = Color(0xCCA4C639),
    onPrimary = Color.White,
    onPrimaryContainer = Color.White,
    onSurface = Color(0xFFE6E0E9),
    onSurfaceVariant = Color.White,
    error = Color.Red
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
    surfaceContainerLow = Color.White,
    surfaceContainerHighest = Color(0xFF769A04).copy(alpha = 0.30f),
    outline = Color(0xCCA4C639),
    onPrimary = Color.Black,
    onSurface = Color(0xFF1D1B20),
    onSurfaceVariant = Color.White
)

val themeShapes = Shapes(
    extraSmall = RoundedCornerShape(4.0.dp),
    small = RoundedCornerShape(8.0.dp),
    medium = RoundedCornerShape(12.0.dp),
    large = RoundedCornerShape(16.0.dp),
    extraLarge = RoundedCornerShape(28.0.dp)
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
        shapes = themeShapes,
        typography = droidDevTipsTypography(),
        content = content
    )
}
