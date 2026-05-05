package com.droiddevtips.spotlight.spotlight.data

import androidx.compose.ui.graphics.Color

/**
 * The spotlight type sealed class
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
sealed class SpotlightType {
    data class Rect(
        val radius: Int = 16,
        val strokeWidth: Int = 2,
        val strokeColor: Color = Color.White,
        val overlayColor: Color = Color.Black
    ) : SpotlightType()

    data class Circle(val overlayColor: Color = Color.Black, val radius: Float = 0f) : SpotlightType()
}