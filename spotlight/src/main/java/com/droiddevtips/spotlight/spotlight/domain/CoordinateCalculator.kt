package com.droiddevtips.spotlight.spotlight.domain

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.droiddevtips.spotlight.spotlight.data.DisplayCoordinates
import com.droiddevtips.spotlight.spotlight.data.SpotlightInfo
import com.droiddevtips.spotlight.spotlight.data.SpotlightType

/**
 * The coordinate calculator
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class CoordinateCalculator {

    companion object {
        fun calculate(
            context: Context,
            density: Density,
            containerSize: IntSize,
            spotlightInfo: SpotlightInfo,
            spotlightType: SpotlightType,
            overlayRootOffset: Offset = Offset.Zero
        ): DisplayCoordinates {

            val spotPad = with(density) { spotlightInfo.spotPadding.dp.toPx() }
            val spotLeft = (spotlightInfo.bounds.left) - overlayRootOffset.x - spotPad
            val spotTop = (spotlightInfo.bounds.top) - overlayRootOffset.y - spotPad
            val spotRight = (spotlightInfo.bounds.right) - overlayRootOffset.x + spotPad
            val spotBottom = (spotlightInfo.bounds.bottom) - overlayRootOffset.y + spotPad
            val spotCenterX = (spotLeft + spotRight) / 2f
            val spotCenterY = (spotTop + spotBottom) / 2f

            val lineCoordinates by LargestSpacingCalculator(
                context = context,
                spotTop = spotTop,
                spotBottom = spotBottom,
                spotLeft = spotLeft,
                spotRight = spotRight,
                spotlightType = spotlightType,
                containerSize = containerSize
            )

            return DisplayCoordinates(
                spotlightPadding = spotPad,
                spotlightLeft = spotLeft,
                spotlightTop = spotTop,
                spotlightRight = spotRight,
                spotlightBottom = spotBottom,
                spotlightItemCenterX = spotCenterX,
                spotlightItemCenterY = spotCenterY,
                textCoordinate = lineCoordinates
            )
        }
    }
}