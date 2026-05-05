package com.droiddevtips.spotlight.spotlight.data

/**
 * The display coordinates data model
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
    val textCoordinate: TextCoordinate = TextCoordinate.None,
)