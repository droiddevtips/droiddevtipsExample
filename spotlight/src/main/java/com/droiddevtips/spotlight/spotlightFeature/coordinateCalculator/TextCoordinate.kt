package com.droiddevtips.spotlight.spotlightFeature.coordinateCalculator

import androidx.compose.ui.geometry.Offset

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
sealed class TextCoordinate(val lineStartCoordinate: Offset, val lineEndCoordinate: Offset) {

    data class PortraitRightBottom(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
//    data class PortraitBottomCenter(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
//    data class PortraitLeftVerticalBottom(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data class PortraitRightTop(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data class PortraitLeftBottom(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data class PortraitLeftTop(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data class PortraitVerticalCenterLeft(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data class PortraitVerticalCenterRight(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data class PortraitHorizontalVerticalCenter(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data class PortraitHorizontalCenterTop(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data class PortraitHorizontalCenterBottom(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)

    data class LandscapeRightBottom(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
//    data class LandscapeBottomCenter(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
//    data class LandscapeLeftVerticalBottom(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data class LandscapeRightTop(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data class LandscapeLeftBottom(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data class LandscapeLeftTop(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)

    data class LandscapeVerticalCenterLeft(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data class LandscapeVerticalCenterRight(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data class LandscapeHorizontalVerticalCenter(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data class LandscapeHorizontalCenterTop(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data class LandscapeHorizontalCenterBottom(private val _lineStartCoordinate: Offset, private val _lineEndCoordinate: Offset) : TextCoordinate(_lineStartCoordinate, _lineEndCoordinate)
    data object None : TextCoordinate(Offset.Zero, Offset.Zero)

}