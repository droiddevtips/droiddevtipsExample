package com.droiddevtips.spotlight.spotlightFeature.coordinateCalculator

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextLayoutResult
import kotlin.reflect.KProperty

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class TextCoordinateCalculator(
    private val textAreaCoordinate: TextCoordinate,
    private val textLayout: TextLayoutResult
) : ReadOnlyProperty<Any?, Offset> {

    private val textPadding = 20f

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): Offset {

        Log.i("TAG23", "\nTextCoordinate -> $textAreaCoordinate\nText layout Result -> $textLayout\n")

        return when(textAreaCoordinate) {
            is TextCoordinate.LandscapeHorizontalCenterBottom -> getCenterBottomCoordinate()
            is TextCoordinate.LandscapeHorizontalCenterTop -> getCenterTopCoordinate()
            is TextCoordinate.LandscapeHorizontalVerticalCenter -> getCenterTopCoordinate()
            is TextCoordinate.LandscapeLeftBottom -> getCenterBottomCoordinate()
            is TextCoordinate.LandscapeLeftTop -> getCenterTopCoordinate()
            is TextCoordinate.LandscapeRightBottom -> getCenterBottomCoordinate()
            is TextCoordinate.LandscapeRightTop -> getCenterTopCoordinate()
            is TextCoordinate.LandscapeVerticalCenterLeft -> getCenterTopCoordinate()
            is TextCoordinate.LandscapeVerticalCenterRight -> getCenterTopCoordinate()
            is TextCoordinate.PortraitHorizontalCenterBottom -> getCenterBottomCoordinate()
            is TextCoordinate.PortraitHorizontalCenterTop -> getCenterTopCoordinate()
            is TextCoordinate.PortraitHorizontalVerticalCenter -> getCenterTopCoordinate()
            is TextCoordinate.PortraitLeftBottom -> getCenterBottomCoordinate()
            is TextCoordinate.PortraitLeftTop -> getCenterTopCoordinate()
            is TextCoordinate.PortraitRightBottom -> getCenterBottomCoordinate()
            is TextCoordinate.PortraitRightTop -> getCenterTopCoordinate()
            is TextCoordinate.PortraitVerticalCenterLeft -> getCenterTopCoordinate()
            is TextCoordinate.PortraitVerticalCenterRight -> getCenterTopCoordinate()
            TextCoordinate.None -> Offset.Zero
        }
    }

    private fun getCenterBottomCoordinate(): Offset = Offset(
        x = textAreaCoordinate.lineEndCoordinate.x - (textLayout.size.width/2),
        y = textAreaCoordinate.lineEndCoordinate.y + textPadding
    )

    private fun getCenterTopCoordinate(): Offset = Offset(
        x = textAreaCoordinate.lineEndCoordinate.x - (textLayout.size.width/2),
        y = (textAreaCoordinate.lineEndCoordinate.y - textPadding) - textLayout.size.height
    )
}