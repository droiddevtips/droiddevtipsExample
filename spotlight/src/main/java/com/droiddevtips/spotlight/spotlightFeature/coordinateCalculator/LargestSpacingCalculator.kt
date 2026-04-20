package com.droiddevtips.spotlight.spotlightFeature.coordinateCalculator

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import kotlin.math.abs
import kotlin.reflect.KProperty

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class LargestSpacingCalculator(
    private val context: Context,
    private val spotTop: Float,
    private val spotBottom: Float,
    private val spotLeft: Float,
    private val spotRight: Float,
    private val containerSize: IntSize
) : ReadOnlyProperty<Any?, Pair<Offset,Offset>> {

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): Pair<Offset,Offset> {

        val padding = 20f
        val spotTopDist = if (spotTop < 0) 0f else spotTop
        val spotBottomRaw = (containerSize.height - spotBottom)
        val spotBottomDist = if (spotBottomRaw < 0f) 0f else spotBottomRaw
        val spotLeftDist = if (spotLeft < 0f) 0f else spotLeft
        val spotRightDistRaw = (containerSize.width - spotRight)
        val spotRightDist = if (spotRightDistRaw < 0f) 0f else spotRightDistRaw
        val spotCenterX = spotLeft + (abs(spotLeft - spotRight)/2f)
        val spotCenterY = spotTop + (abs(spotTop - spotBottom)/2f)
        val orientation = getScreenOrientation()

        return when(orientation) {

           //TODO: Calculate the line start coordinate as well and test it on table as well

           Configuration.ORIENTATION_LANDSCAPE -> {
               if (spotLeftDist == spotRightDist || abs(spotLeftDist - spotRightDist) <= 150f) {
                   // Horizontal center

                   if (spotTopDist == spotBottomDist || abs(spotTopDist - spotBottomDist) <= 150f) {
                       // Vertical center

                       Pair(
                           Offset(
                               x = (containerSize.width/2f),
                               y = spotTop - padding
                           ),
                           Offset(
                               x = (containerSize.width/2f),
                               y = (spotTopDist/2f)
                           )
                       )

                   } else {
                       val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)

                       when(longestVerticalSpacing) {

                           spotTopDist -> Pair(
                               Offset(
                                   x = (containerSize.width/2f),
                                   y = spotTopDist - padding
                               ),
                               Offset(
                                   x = (containerSize.width/2f),
                                   y = (spotTopDist/2f)
                               )
                           )

                           else -> Pair(
                               Offset(
                                   x = (containerSize.width/2f),
                                   y = spotBottom + padding
                               ),
                               Offset(
                                   x = (containerSize.width/2f),
                                   y = (spotBottomDist/2f)
                               )
                           )
                       }
                   }
               } else if (spotTopDist == spotBottomDist) {
                   // Center vertically

                   val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                   when(longestHorizontalSpacing) {

                       spotLeftDist -> Pair(
                           Offset(
                               x =  spotCenterX,
                               y = (containerSize.height/2f)
                           ),
                           Offset(
                               x = (spotLeftDist/2f),
                               y = (containerSize.height/2f)
                           )
                       )

                       else -> {
                           val horizontalCoordinate = ((containerSize.width - spotRightDist) + (spotRightDist/2f))
                           Pair(
                               Offset(
                                   x = spotRightDist,
                                   y = spotCenterY + spotRight
                               ),
                               Offset(
                                   x = horizontalCoordinate,
                                   y = (containerSize.height/2f)
                               )
                           )
                       }
                   }
               } else {

                   val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)
                   val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                   when(longestHorizontalSpacing) {

                       spotLeftDist -> {

                           when(longestVerticalSpacing) {

                               spotTopDist -> Pair(
                                   Offset(
                                       x = spotLeft - padding,
                                       y = spotTop + (abs(spotTop - spotBottom)/2f)
                                   ),
                                   Offset(
                                       x = (containerSize.width/2f),
                                       y = spotTopDist/2f
                                   )
                               )

                               spotBottomDist -> Pair(
                                   Offset(
                                       x = spotLeft - padding,
                                       y = spotTop + (abs(spotTop - spotBottom)/2f)
                                   ),
                                   Offset(
                                       x = (containerSize.width/4f) * 3,
                                       y = spotBottomDist/2f
                                   )
                               )

                               else -> Pair(
                                   Offset.Zero,
                                   Offset.Zero
                               )
                           }
                       }

                       else -> {

                           when(longestVerticalSpacing) {

                               spotTopDist -> Pair(
                                   Offset(
                                       x = spotRight + padding,
                                       y = spotTop + (abs(spotTop - spotBottom)/2f)
                                   ),
                                   Offset(
                                       x = (containerSize.width/3f),
                                       y = spotTopDist/2f
                                   )
                               )

                               spotBottomDist -> Pair(
                                   Offset(
                                       x = spotRight + padding,
                                       y = spotTopDist + abs(spotTop - spotBottom)/2f
                                   ),
                                   Offset(
                                       x = (containerSize.width/3f),
                                       y = (spotBottomDist/2f)
                                   )
                               )

                               else -> Pair(
                                   Offset.Zero,
                                   Offset.Zero
                               )
                           }
                       }
                   }
               }
           }

           Configuration.ORIENTATION_PORTRAIT -> {
               if (spotLeftDist == spotRightDist || abs(spotLeftDist - spotRightDist) <= 150f) {
                   // Horizontal center

                   if (spotTopDist == spotBottomDist || abs(spotTopDist - spotBottomDist) <= 150f) {
                       // Vertical center

                       Pair(
                           Offset(
                               x = spotCenterX, // (abs (spotLeft - spotRight)/2f)
                               y = spotTop - padding
                           ),
                           Offset(
                               x = (containerSize.width/2f),
                               y = (spotTopDist/2f) + (spotTopDist/4f)
                           )
                       )
                   } else {
                       val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)

                       when(longestVerticalSpacing) {

                           spotTopDist -> Pair(
                               Offset(
                                   x = spotCenterX,
                                   y = spotTop - padding
                               ),
                               Offset(
                                   x = (containerSize.width/2f),
                                   y = (spotTopDist/2f) + (spotTopDist/4f)
                               )
                           )

                           else -> Pair(
                               Offset(
                                   x = spotCenterX,
                                   y = spotBottom + padding
                               ),
                               Offset(
                                   x = (containerSize.width/2f),
                                   y = (spotBottomDist/3f)
                               )
                           )
                       }
                   }
               } else if (spotTopDist == spotBottomDist) {
                   // Center vertically

                   val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                   when(longestHorizontalSpacing) {

                       spotLeftDist -> Pair(
                           Offset(
                               x = spotLeft,
                               y = spotCenterY //(containerSize.height/2).toFloat()
                           ),
                           Offset(
                               x = (spotLeftDist/2f),
                               y = (containerSize.height/2f)
                           )
                       )

                       else -> {
                           val horizontalCoordinate = ((containerSize.width - spotRightDist) + (spotRightDist/2f))
                           Pair(
                               Offset(
                                   x = spotRight,
                                   y = spotCenterY
                               ),
                               Offset(
                                   x = horizontalCoordinate,
                                   y = (containerSize.height/2f)
                               )
                           )
                       }
                   }
               } else {

                   val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)
                   val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                   when(longestHorizontalSpacing) {

                       spotLeftDist -> {

                           when(longestVerticalSpacing) {

                               spotTopDist -> Pair(
                                   Offset(
                                       x = spotCenterX,
                                       y = spotTop - padding
                                   ),
                                   Offset(
                                       x = containerSize.width/2f,
                                       y = spotTopDist/2f + (spotTopDist/4f)
                                   )
                               )

                               spotBottomDist -> Pair(
                                   Offset(
                                       x = spotCenterX,
                                       y = spotBottom + padding
                                   ),
                                   Offset(
                                       x = containerSize.width/2f,
                                       y = spotBottom + (spotBottomDist/3f)
                                   )
                               )

                               else -> Pair(
                                   Offset.Zero,
                                   Offset.Zero
                               )
                           }
                       }

                       else -> {

                           when(longestVerticalSpacing) {

                               spotTopDist -> Pair(
                                   Offset(
                                       x = spotCenterX,
                                       y = spotTop - padding
                                   ),
                                   Offset(
                                       x = (containerSize.width/2f),
                                       y = (containerSize.height/2f) + (spotTopDist/4f)
                                   )
                               )

                               spotBottomDist -> Pair(
                                   Offset(
                                       x = spotCenterX,
                                       y = spotBottom + padding
                                   ),
                                   Offset(
                                       x = (containerSize.width/2f),
                                       y = spotBottom + (spotBottomDist/3f)
                                   )
                               )

                               else -> Pair(
                                   Offset.Zero,
                                   Offset.Zero
                               )
                           }
                       }
                   }
               }
           }

           else -> Pair(Offset.Zero, Offset.Zero)
       }
    }

    private fun getScreenOrientation(): Int {
        val configuration = context.resources.configuration
        return configuration.orientation
    }

}