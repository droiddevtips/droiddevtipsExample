package com.droiddevtips.spotlight.spotlightFeature

import androidx.compose.ui.geometry.Offset

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
data class DisplayCoordinates(
    val spotlightPadding: Float = 0f,
    val spotlightLeft: Float = 0f,
    val spotlightTop: Float = 0f,
    val spotlightRight: Float = 0f,
    val spotlightBottom: Float = 0f,
    val spotlightItemCenterX: Float = 0f,
    val spotlightItemCenterY: Float = 0f,
    val lineStartCoordinate: Offset = Offset.Zero,
    val lineEndCoordinate: Offset = Offset.Zero
)
