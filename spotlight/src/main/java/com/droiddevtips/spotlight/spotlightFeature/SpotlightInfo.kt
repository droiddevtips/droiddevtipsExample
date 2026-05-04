package com.droiddevtips.spotlight.spotlightFeature

import androidx.compose.ui.geometry.Rect

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
data class SpotlightInfo(
    val id: String,
    val bounds: Rect,
    val text: String,
    val spotPadding: Int = 10,
    val type: SpotlightType
)
