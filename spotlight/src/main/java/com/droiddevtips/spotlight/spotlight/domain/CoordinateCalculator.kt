package com.droiddevtips.spotlight.spotlight.domain

import android.content.Context
import android.util.Log
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
            overlayRootOffset: Offset = Offset.Companion.Zero
        ): DisplayCoordinates {
            // Convert boundsInRoot() → canvas-local by subtracting the overlay's own
            // root offset. When the overlay is at (0,0) this is a no-op; when it is
            // shifted (e.g. by status-bar insets above the composable), it corrects
            // the mismatch so the spotlight lands exactly on the target.
            Log.i("TAG45", "Overlay root offset 23: $overlayRootOffset")
            val spotPad =
                with(density) { spotlightInfo.spotPadding.dp.toPx() } // adds 20dp of breathing room around the target component's bounds on all four sides
            val spotLeft =
                (spotlightInfo.bounds.left) - overlayRootOffset.x - spotPad // canvas-local left edge, then extend 20dp further left (spotlight grows outward)
            val spotTop = (spotlightInfo.bounds.top) - overlayRootOffset.y - spotPad
            //(info?.bounds?.top    ?: 0f) = the top edge of the highlighted component, in root coordinates (or 0 if nothing is highlighted)
            //- overlayRootOffset.y  =  converts from root coordinates to canvas-local coordinates (corrects for any vertical shift of the overlay, e.g. status bar insets)
            //- spotPad   =  extends the spotlight 20dp upward (smaller y = higher on screen) to add breathing room above the component
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

            Log.i("TAG48", "Line coordinates -> $lineCoordinates")

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