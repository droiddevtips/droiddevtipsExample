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
) : ReadOnlyProperty<Any?, TextCoordinate> {

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): TextCoordinate {

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

        Log.i("TAG56","Top -> $spotTopDist")
        Log.i("TAG56","bottom -> $spotBottomDist")

        return when(orientation) {

           //TODO: Calculate the line start coordinate as well and test it on table as well

           Configuration.ORIENTATION_LANDSCAPE -> {
               if (spotLeftDist == spotRightDist || abs(spotLeftDist - spotRightDist) <= 150f) {
                   // Horizontal center
                   Log.i("TAG56","Landscape Horizontal center")

                   if (spotTopDist == spotBottomDist || abs(spotTopDist - spotBottomDist) <= 150f) {
                       // Vertical center
                       Log.i("TAG56","Landscape Vertical center")

                       TextCoordinate.LandscapeHorizontalVerticalCenter(
                           _lineStartCoordinate = Offset(
                               x = (containerSize.width/2f),
                               y = spotTop - padding
                           ),
                           _lineEndCoordinate = Offset(
                               x = (containerSize.width/2f),
                               y = (spotTopDist/2f)
                           )
                       )
                   } else {
                       val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)

                       when(longestVerticalSpacing) {

                           spotTopDist -> {
                               Log.i("TAG56","Landscape Top")
                               TextCoordinate.LandscapeHorizontalCenterTop(
                                   _lineStartCoordinate = Offset(
                                       x = (containerSize.width/2f),
                                       y = spotTopDist - padding
                                   ),
                                   _lineEndCoordinate = Offset(
                                       x = (containerSize.width/2f),
                                       y = (spotTopDist/2f)
                                   )
                               )
                           }

                           else -> {
                               Log.i("TAG56","Landscape Bottom")
                               TextCoordinate.LandscapeHorizontalCenterBottom(
                                   _lineStartCoordinate = Offset(
                                       x = (containerSize.width/2f),
                                       y = spotBottom + padding
                                   ),
                                   _lineEndCoordinate = Offset(
                                       x = (containerSize.width/2f),
                                       y = (spotBottomDist/2f)
                                   )
                               )
                           }
                       }
                   }
               } else if (spotTopDist == spotBottomDist) {
                   // Center vertically
                   Log.i("TAG56","Landscape Center vertically")
                   val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                   when(longestHorizontalSpacing) {

                       spotLeftDist -> {
                           Log.i("TAG56","Landscape Left")

                           TextCoordinate.LandscapeVerticalCenterLeft(
                               _lineStartCoordinate = Offset(
                                   x = spotLeft - padding,
                                   y = spotCenterY
                               ),
                               _lineEndCoordinate = Offset(
                                   x = (spotLeftDist/3f)*2,
                                   y = (containerSize.height/3f)
                               )
                           )
                       }

                       else -> {
                           Log.i("TAG56","Landscape Right")
                           val horizontalCoordinate = ((containerSize.width - spotRightDist) + (spotRightDist/2f))

                           TextCoordinate.LandscapeVerticalCenterRight(
                               _lineStartCoordinate = Offset(
                                   x = spotRight + padding,
                                   y = spotCenterY
                               ),
                               _lineEndCoordinate = Offset(
                                   x = horizontalCoordinate/2f,
                                   y = (containerSize.height/3f)
                               )
                           )
                       }
                   }
               } else {

                   val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)
                   val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                   when(longestHorizontalSpacing) {

                       spotLeftDist -> {
                           Log.i("TAG56","Landscape Left")
                           when(longestVerticalSpacing) {

                               spotTopDist -> {
                                   Log.i("TAG56","Landscape vertical Top")
                                   Log.i("TAG56","Top -> $spotTopDist")
                                   Log.i("TAG56","Bottom -> $spotBottomDist")
                                   Log.i("TAG56","Landscape vertical Top")

                                   TextCoordinate.LandscapeLeftTop(
                                       _lineStartCoordinate = Offset(
                                           x = spotLeft - padding,
                                           y = spotTop + (abs(spotTop - spotBottom)/2f)
                                       ),
                                       _lineEndCoordinate = Offset(
                                           x = (containerSize.width/2f),
                                           y = spotTopDist/2f
                                       )
                                   )
                               }

                               spotBottomDist -> {
                                   Log.i("TAG56","Landscape vertical Bottom")
                                   Log.i("TAG56","Dis spot top -> ${spotTopDist}")
                                   Log.i("TAG56","Dis spot bottom -> ${spotBottomDist}")
                                   Log.i("TAG56","Landscape vertical Bottom")

                                   TextCoordinate.LandscapeLeftBottom(
                                       _lineStartCoordinate = Offset(
                                           x = spotLeft - padding,
                                           y = spotTop + (abs(spotTop - spotBottom)/2f)
                                       ),
                                       _lineEndCoordinate = Offset(
                                           x = (containerSize.width/3f) * 2,
                                           y = spotTopDist + (spotBottomDist/2f)
                                       )
                                   )
                               }

                               else -> TextCoordinate.None
                           }
                       }

                       else -> {
                           Log.i("TAG56","Landscape Right")
                           when(longestVerticalSpacing) {

                               spotTopDist -> {
                                   Log.i("TAG56","Landscape vertical Top")
                                   Log.i("TAG56","Top -> $spotTopDist")
                                   Log.i("TAG56","Bottom -> $spotBottomDist")
                                   Log.i("TAG56","Landscape vertical Top")
                                   TextCoordinate.LandscapeRightTop(
                                       _lineStartCoordinate = Offset(
                                           x = spotRight + padding,
                                           y = spotTop + (abs(spotTop - spotBottom)/2f)
                                       ),
                                       _lineEndCoordinate = Offset(
                                           x = (containerSize.width/2f),
                                           y = spotTopDist/2f
                                       )
                                   )
                               }

                               spotBottomDist -> {
                                   Log.i("TAG56","Landscape vertical Bottom")

                                   TextCoordinate.LandscapeRightBottom(
                                       _lineStartCoordinate = Offset(
                                           x = spotRight + padding,
                                           y = spotTopDist + abs(spotTop - spotBottom)/2f
                                       ),
                                       _lineEndCoordinate = Offset(
                                           x = (containerSize.width/3f),
                                           y = spotTopDist + (spotBottomDist/2f)
                                       )
                                   )
                               }

                               else -> TextCoordinate.None
                           }
                       }
                   }
               }
           }

           Configuration.ORIENTATION_PORTRAIT -> {
               if (spotLeftDist == spotRightDist || abs(spotLeftDist - spotRightDist) <= 150f) {
                   // Horizontal center
                   Log.i("TAG56","Portrait Horizontal center")
                   if (spotTopDist == spotBottomDist || abs(spotTopDist - spotBottomDist) <= 150f) {
                       // Vertical center
                       Log.i("TAG56","Portrait Vertical center")

                       TextCoordinate.PortraitHorizontalVerticalCenter(
                           _lineStartCoordinate = Offset(
                               x = spotCenterX,
                               y = spotTop - padding
                           ),
                           _lineEndCoordinate = Offset(
                               x = (containerSize.width/2f),
                               y = (spotTopDist/2f) + (spotTopDist/4f)
                           )
                       )
                   } else {
                       val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)

                       when(longestVerticalSpacing) {

                           spotTopDist -> {
                               Log.i("TAG56","Portrait Top")

                               TextCoordinate.PortraitHorizontalCenterTop(
                                   _lineStartCoordinate = Offset(
                                       x = spotCenterX,
                                       y = spotTop - padding
                                   ),
                                   _lineEndCoordinate = Offset(
                                       x = (containerSize.width/2f),
                                       y = (spotTopDist/2f) + (spotTopDist/4f)
                                   )
                               )
                           }

                           else -> {
                               Log.i("TAG56","Portrait Bottom")

                               TextCoordinate.PortraitHorizontalCenterBottom(
                                   _lineStartCoordinate = Offset(
                                       x = spotCenterX,
                                       y = spotBottom + padding
                                   ),
                                   _lineEndCoordinate = Offset(
                                       x = (containerSize.width/2f),
                                       y = (spotBottomDist/3f)
                                   )
                               )
                           }
                       }
                   }
               } else if (spotTopDist == spotBottomDist) {
                   // Center vertically
                   Log.i("TAG56","Portrait Center vertically")
                   val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                   when(longestHorizontalSpacing) {

                       spotLeftDist -> {
                           Log.i("TAG56","Portrait Left")

                           TextCoordinate.PortraitVerticalCenterLeft(
                               _lineStartCoordinate = Offset(
                                   x = spotCenterX,
                                   y = spotTop - padding
                               ),
                               _lineEndCoordinate = Offset(
                                   x = (containerSize.width/2f),
                                   y = (containerSize.height/3f)
                               )
                           )
                       }

                       else -> {
                           Log.i("TAG56","Portrait right")
                           TextCoordinate.PortraitVerticalCenterRight(
                               _lineStartCoordinate = Offset(
                                   x = spotCenterX,
                                   y = spotTop - padding
                               ),
                               _lineEndCoordinate = Offset(
                                   x = (containerSize.width/2f),
                                   y = (containerSize.height/3f)
                               )
                           )
                       }
                   }
               } else {

                   val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)
                   val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                   when(longestHorizontalSpacing) {

                       spotLeftDist -> {
                           Log.i("TAG56","Portrait left")
                           when(longestVerticalSpacing) {

                               spotTopDist -> {
                                   Log.i("TAG56","Portrait top")

                                   TextCoordinate.PortraitLeftTop(
                                       _lineStartCoordinate = Offset(
                                           x = spotCenterX,
                                           y = spotTop - padding
                                       ),
                                       _lineEndCoordinate = Offset(
                                           x = containerSize.width/2f,
                                           y = spotTopDist/2f + (spotTopDist/4f)
                                       )
                                   )
                               }

                               spotBottomDist -> {
                                   Log.i("TAG56","Portrait bottom")

                                   TextCoordinate.PortraitLeftBottom(
                                       _lineStartCoordinate = Offset(
                                           x = spotCenterX,
                                           y = spotBottom + padding
                                       ),
                                       _lineEndCoordinate = Offset(
                                           x = containerSize.width/2f,
                                           y = spotBottom + (spotBottomDist/3f)
                                       )
                                   )
                               }

                               else -> TextCoordinate.None
                           }
                       }

                       else -> {
                           Log.i("TAG56","Portrait right")
                           when(longestVerticalSpacing) {

                               spotTopDist -> {
                                   Log.i("TAG56","Portrait top")

                                   TextCoordinate.PortraitRightTop(
                                       _lineStartCoordinate = Offset(
                                           x = spotCenterX,
                                           y = spotTop - padding
                                       ),
                                       _lineEndCoordinate = Offset(
                                           x = (containerSize.width/2f),
                                           y = (containerSize.height/2f) + (spotTopDist/4f)
                                       )
                                   )
                               }

                               spotBottomDist -> {
                                   Log.i("TAG56","Portrait bottom")

                                   TextCoordinate.PortraitRightBottom(
                                       _lineStartCoordinate = Offset(
                                           x = spotCenterX,
                                           y = spotBottom + padding
                                       ),
                                       _lineEndCoordinate = Offset(
                                           x = (containerSize.width/2f),
                                           y = spotBottom + (spotBottomDist/3f)
                                       )
                                   )
                               }

                               else -> TextCoordinate.None
                           }
                       }
                   }
               }
           }

           else -> TextCoordinate.None
       }
    }

    private fun getScreenOrientation(): Int {
        val configuration = context.resources.configuration
        return configuration.orientation
    }

}