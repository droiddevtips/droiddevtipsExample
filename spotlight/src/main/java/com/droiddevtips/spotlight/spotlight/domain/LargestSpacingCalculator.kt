package com.droiddevtips.spotlight.spotlight.domain

import android.content.Context
import android.content.res.Configuration
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import com.droiddevtips.spotlight.delegates.ReadOnlyProperty
import com.droiddevtips.spotlight.spotlight.data.SpotlightType
import com.droiddevtips.spotlight.spotlight.data.TextCoordinate
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.reflect.KProperty

/**
 * The largest spacing calculator delegate, a sub class of [ReadOnlyProperty].
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class LargestSpacingCalculator(
    private val context: Context,
    private val spotTop: Float,
    private val spotBottom: Float,
    private val spotLeft: Float,
    private val spotRight: Float,
    private val containerSize: IntSize,
    private val spotlightType: SpotlightType,
) : ReadOnlyProperty<Any?, TextCoordinate> {

    private var spotCenter: Offset = Offset.Zero
    private var spotWidth: Float = 0f
    private var spotHeight: Float = 0f
    private var radius: Float = 20f
    private val padding: Float = 20f

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): TextCoordinate {

        val spotTopDist = if (spotTop < 0) 0f else spotTop
        val spotBottomRaw = (containerSize.height - spotBottom)
        val spotBottomDist = if (spotBottomRaw < 0f) 0f else spotBottomRaw
        val spotLeftDist = if (spotLeft < 0f) 0f else spotLeft
        val spotRightDistRaw = (containerSize.width - spotRight)
        val spotRightDist = if (spotRightDistRaw < 0f) 0f else spotRightDistRaw
        val spotCenterX = spotLeft + (abs(spotLeft - spotRight) / 2f)
        val spotCenterY = spotTop + (abs(spotTop - spotBottom) / 2f)
        val orientation = getScreenOrientation()

        spotCenter = Offset(x = spotCenterX, y = spotCenterY)
        spotWidth = abs(spotLeftDist - if (spotRight < 0f) 0f else spotRight)
        spotHeight = abs(spotTopDist - if (spotBottom < 0f) 0f else spotBottom)
        radius = (sqrt(spotWidth.pow(2) + spotHeight.pow(2)) / 2f)

        return when (spotlightType) {

            is SpotlightType.Circle -> when (orientation) {

                Configuration.ORIENTATION_LANDSCAPE -> {
                    if (spotLeftDist == spotRightDist || abs(spotLeftDist - spotRightDist) <= 150f) {
                        // Horizontal center

                        if (spotTopDist == spotBottomDist || abs(spotTopDist - spotBottomDist) <= 150f) {
                            // Vertical center

                            val coordinates = getCircleLineStarEndOffset(
                                target = Offset(
                                    x = (containerSize.width / 2f),
                                    y = (spotTopDist / 2f)
                                )
                            )

                            TextCoordinate.LandscapeHorizontalVerticalCenter(
                                _lineStartCoordinate = coordinates.second,
                                _lineEndCoordinate = coordinates.first
                            )
                        } else {
                            val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)

                            when (longestVerticalSpacing) {

                                spotTopDist -> {
                                    // Landscape Top
                                    val coordinates = getCircleLineStarEndOffset(
                                        target = Offset(
                                            x = (containerSize.width / 2f),
                                            y = (spotTopDist / 2f)
                                        )
                                    )

                                    TextCoordinate.LandscapeHorizontalCenterTop(
                                        _lineStartCoordinate = coordinates.second,
                                        _lineEndCoordinate = coordinates.first
                                    )
                                }

                                else -> {
                                    // Landscape Bottom
                                    val coordinates = getCircleLineStarEndOffset(
                                        target = Offset(
                                            x = (containerSize.width / 2f),
                                            y = (spotBottomDist / 2f)
                                        )
                                    )

                                    TextCoordinate.LandscapeHorizontalCenterBottom(
                                        _lineStartCoordinate = coordinates.second,
                                        _lineEndCoordinate = coordinates.first
                                    )
                                }
                            }
                        }
                    } else if (spotTopDist == spotBottomDist) {
                        // Center vertically
                        val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                        when (longestHorizontalSpacing) {

                            spotLeftDist -> {
                                // Landscape Left
                                val coordinates = getCircleLineStarEndOffset(
                                    target = Offset(
                                        x = (spotLeftDist / 3f) * 2,
                                        y = (containerSize.height / 3f)
                                    )
                                )

                                TextCoordinate.LandscapeVerticalCenterLeft(
                                    _lineStartCoordinate = coordinates.second,
                                    _lineEndCoordinate = coordinates.first
                                )
                            }

                            else -> {
                                // Landscape Right
                                val horizontalCoordinate =
                                    ((containerSize.width - spotRightDist) + (spotRightDist / 2f))

                                val coordinates = getCircleLineStarEndOffset(
                                    target = Offset(
                                        x = horizontalCoordinate / 2f,
                                        y = (containerSize.height / 3f)
                                    )
                                )

                                TextCoordinate.LandscapeVerticalCenterRight(
                                    _lineStartCoordinate = coordinates.second,
                                    _lineEndCoordinate = coordinates.first
                                )
                            }
                        }
                    } else {

                        val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)
                        val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                        when (longestHorizontalSpacing) {

                            spotLeftDist -> {
                                // Landscape Left
                                when (longestVerticalSpacing) {

                                    spotTopDist -> {
                                        // Landscape vertical Top
                                        val coordinates = getCircleLineStarEndOffset(
                                            target = Offset(
                                                x = (containerSize.width / 2f),
                                                y = spotTopDist / 2f
                                            )
                                        )

                                        TextCoordinate.LandscapeLeftTop(
                                            _lineStartCoordinate = coordinates.second,
                                            _lineEndCoordinate = coordinates.first
                                        )
                                    }

                                    spotBottomDist -> {
                                        // Landscape vertical Bottom
                                        val coordinates = getCircleLineStarEndOffset(
                                            target = Offset(
                                                x = (containerSize.width / 3f) * 2,
                                                y = spotTopDist + (spotBottomDist / 2f)
                                            )
                                        )

                                        TextCoordinate.LandscapeLeftBottom(
                                            _lineStartCoordinate = coordinates.second,
                                            _lineEndCoordinate = coordinates.first
                                        )
                                    }

                                    else -> TextCoordinate.None
                                }
                            }

                            else -> {
                                //landscape right
                                when (longestVerticalSpacing) {

                                    spotTopDist -> {
                                        // Landscape vertical Top
                                        val coordinates = getCircleLineStarEndOffset(
                                            target = Offset(
                                                x = (containerSize.width / 2f),
                                                y = spotTopDist / 2f
                                            )
                                        )

                                        TextCoordinate.LandscapeRightTop(
                                            _lineStartCoordinate = coordinates.second,
                                            _lineEndCoordinate = coordinates.first
                                        )
                                    }

                                    spotBottomDist -> {
                                        //Landscape vertical Bottom
                                        val coordinates = getCircleLineStarEndOffset(
                                            target = Offset(
                                                x = (containerSize.width / 3f),
                                                y = spotTopDist + (spotBottomDist / 2f)
                                            )
                                        )

                                        TextCoordinate.LandscapeRightBottom(
                                            _lineStartCoordinate = coordinates.second,
                                            _lineEndCoordinate = coordinates.first
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
                        if (spotTopDist == spotBottomDist || abs(spotTopDist - spotBottomDist) <= 150f) {
                            // Vertical center
                            val coordinates = getCircleLineStarEndOffset(
                                target = Offset(
                                    x = (containerSize.width / 2f),
                                    y = (spotTopDist / 2f) + (spotTopDist / 4f)
                                )
                            )

                            TextCoordinate.PortraitHorizontalVerticalCenter(
                                _lineStartCoordinate = coordinates.second,
                                _lineEndCoordinate = coordinates.first
                            )
                        } else {
                            val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)

                            when (longestVerticalSpacing) {

                                spotTopDist -> {
                                    //Portrait Top
                                    val coordinates = getCircleLineStarEndOffset(
                                        target = Offset(
                                            x = (containerSize.width / 2f),
                                            y = (spotTopDist / 2f) + (spotTopDist / 4f)
                                        )
                                    )

                                    TextCoordinate.PortraitHorizontalCenterTop(
                                        _lineStartCoordinate = coordinates.second,
                                        _lineEndCoordinate = coordinates.first
                                    )
                                }

                                else -> {
                                    //Portrait Bottom
                                    val coordinates = getCircleLineStarEndOffset(
                                        target = Offset(
                                            x = (containerSize.width / 2f),
                                            y = (spotBottomDist / 3f)
                                        )
                                    )

                                    TextCoordinate.PortraitHorizontalCenterBottom(
                                        _lineStartCoordinate = coordinates.second,
                                        _lineEndCoordinate = coordinates.first
                                    )
                                }
                            }
                        }
                    } else if (spotTopDist == spotBottomDist) {
                        // Center vertically
                        val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                        when (longestHorizontalSpacing) {

                            spotLeftDist -> {
                                //Portrait Left
                                val coordinates = getCircleLineStarEndOffset(
                                    target = Offset(
                                        x = (containerSize.width / 2f),
                                        y = (containerSize.height / 3f)
                                    )
                                )

                                TextCoordinate.PortraitVerticalCenterLeft(
                                    _lineStartCoordinate = coordinates.second,
                                    _lineEndCoordinate = coordinates.first
                                )
                            }

                            else -> {
                                //Portrait right
                                val coordinates = getCircleLineStarEndOffset(
                                    target = Offset(
                                        x = (containerSize.width / 2f),
                                        y = (containerSize.height / 3f)
                                    )
                                )

                                TextCoordinate.PortraitVerticalCenterRight(
                                    _lineStartCoordinate = coordinates.second,
                                    _lineEndCoordinate = coordinates.first
                                )
                            }
                        }
                    } else {

                        val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)
                        val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                        when (longestHorizontalSpacing) {

                            spotLeftDist -> {
                                //Portrait left
                                when (longestVerticalSpacing) {

                                    spotTopDist -> {
                                        //Portrait top
                                        val coordinates = getCircleLineStarEndOffset(
                                            target = Offset(
                                                x = containerSize.width / 2f,
                                                y = spotTopDist / 2f + (spotTopDist / 4f)
                                            )
                                        )

                                        TextCoordinate.PortraitLeftTop(
                                            _lineStartCoordinate = coordinates.second,
                                            _lineEndCoordinate = coordinates.first
                                        )
                                    }

                                    spotBottomDist -> {
                                        //Portrait bottom
                                        val coordinates = getCircleLineStarEndOffset(
                                            target = Offset(
                                                x = containerSize.width / 2f,
                                                y = spotBottom + (spotBottomDist / 3f)
                                            )
                                        )

                                        TextCoordinate.PortraitLeftBottom(
                                            _lineStartCoordinate = coordinates.second,
                                            _lineEndCoordinate = coordinates.first
                                        )
                                    }

                                    else -> TextCoordinate.None
                                }
                            }

                            else -> {
                                // Portrait right
                                when (longestVerticalSpacing) {

                                    spotTopDist -> {
                                        // Portrait top
                                        val coordinates = getCircleLineStarEndOffset(
                                            target = Offset(
                                                x = (containerSize.width / 2f),
                                                y = (containerSize.height / 2f) + (spotTopDist / 4f)
                                            )
                                        )

                                        TextCoordinate.PortraitRightTop(
                                            _lineStartCoordinate = coordinates.second,
                                            _lineEndCoordinate = coordinates.first
                                        )
                                    }

                                    spotBottomDist -> {
                                        // Portrait bottom
                                        val coordinates = getCircleLineStarEndOffset(
                                            target = Offset(
                                                x = (containerSize.width / 2f),
                                                y = spotBottom + (spotBottomDist / 3f)
                                            )
                                        )

                                        TextCoordinate.PortraitRightBottom(
                                            _lineStartCoordinate = coordinates.second,
                                            _lineEndCoordinate = coordinates.first
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

            is SpotlightType.Rect -> when (orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    if (spotLeftDist == spotRightDist || abs(spotLeftDist - spotRightDist) <= 150f) {
                        // Horizontal center
                        if (spotTopDist == spotBottomDist || abs(spotTopDist - spotBottomDist) <= 150f) {
                            // Vertical center
                            val coordinates = getRectLineStarEndOffset(
                                targetOffset = Offset(
                                    x = (containerSize.width / 2f),
                                    y = (spotTopDist / 2f)
                                )
                            )

                            TextCoordinate.LandscapeHorizontalVerticalCenter(
                                _lineStartCoordinate = coordinates.first,
                                _lineEndCoordinate = coordinates.second
                            )
                        } else {
                            val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)

                            when (longestVerticalSpacing) {

                                spotTopDist -> {
                                    // Landscape Top
                                    val coordinates = getRectLineStarEndOffset(
                                        targetOffset = Offset(
                                            x = (containerSize.width / 2f),
                                            y = (spotTopDist / 2f)
                                        )
                                    )

                                    TextCoordinate.LandscapeHorizontalCenterTop(
                                        _lineStartCoordinate = coordinates.first,
                                        _lineEndCoordinate = coordinates.second
                                    )
                                }

                                else -> {
                                    // Landscape Bottom
                                    val coordinates = getRectLineStarEndOffset(
                                        targetOffset = Offset(
                                            x = (containerSize.width / 2f),
                                            y = (spotBottomDist / 2f)
                                        )
                                    )

                                    TextCoordinate.LandscapeHorizontalCenterBottom(
                                        _lineStartCoordinate = coordinates.first,
                                        _lineEndCoordinate = coordinates.second
                                    )
                                }
                            }
                        }
                    } else if (spotTopDist == spotBottomDist) {
                        // Center vertically
                        val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                        when (longestHorizontalSpacing) {

                            spotLeftDist -> {
                                // Landscape Left
                                val coordinates = getRectLineStarEndOffset(
                                    targetOffset = Offset(
                                        x = (spotLeftDist / 3f) * 2,
                                        y = (containerSize.height / 3f)
                                    )
                                )

                                TextCoordinate.LandscapeVerticalCenterLeft(
                                    _lineStartCoordinate = coordinates.first,
                                    _lineEndCoordinate = coordinates.second
                                )
                            }

                            else -> {
                                // Landscape Right
                                val horizontalCoordinate =
                                    ((containerSize.width - spotRightDist) + (spotRightDist / 2f))

                                val coordinates = getRectLineStarEndOffset(
                                    targetOffset = Offset(
                                        x = horizontalCoordinate / 2f,
                                        y = (containerSize.height / 3f)
                                    )
                                )

                                TextCoordinate.LandscapeVerticalCenterRight(
                                    _lineStartCoordinate = coordinates.first,
                                    _lineEndCoordinate = coordinates.second
                                )
                            }
                        }
                    } else {

                        val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)
                        val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                        when (longestHorizontalSpacing) {

                            spotLeftDist -> {
                                // Landscape Left
                                when (longestVerticalSpacing) {

                                    spotTopDist -> {
                                        // Landscape vertical Top
                                        val coordinates = getRectLineStarEndOffset(
                                            targetOffset = Offset(
                                                x = (containerSize.width / 2f),
                                                y = spotTopDist / 2f
                                            )
                                        )

                                        TextCoordinate.LandscapeLeftTop(
                                            _lineStartCoordinate = coordinates.first,
                                            _lineEndCoordinate = coordinates.second
                                        )
                                    }

                                    spotBottomDist -> {
                                        // Landscape vertical Bottom
                                        val coordinates = getRectLineStarEndOffset(
                                            targetOffset = Offset(
                                                x = (containerSize.width / 3f) * 2,
                                                y = spotTopDist + (spotBottomDist / 2f)
                                            )
                                        )

                                        TextCoordinate.LandscapeLeftBottom(
                                            _lineStartCoordinate = coordinates.first,
                                            _lineEndCoordinate = coordinates.second
                                        )
                                    }

                                    else -> TextCoordinate.None
                                }
                            }

                            else -> {
                                // Landscape Right
                                when (longestVerticalSpacing) {

                                    spotTopDist -> {
                                        // Landscape vertical Top
                                        val coordinates = getRectLineStarEndOffset(
                                            targetOffset = Offset(
                                                x = (containerSize.width / 2f),
                                                y = spotTopDist / 2f
                                            )
                                        )

                                        TextCoordinate.LandscapeRightTop(
                                            _lineStartCoordinate = coordinates.first,
                                            _lineEndCoordinate = coordinates.second
                                        )
                                    }

                                    spotBottomDist -> {
                                        // Landscape vertical Bottom
                                        val coordinates = getRectLineStarEndOffset(
                                            targetOffset = Offset(
                                                x = (containerSize.width / 3f),
                                                y = spotTopDist + (spotBottomDist / 2f)
                                            )
                                        )

                                        TextCoordinate.LandscapeRightBottom(
                                            _lineStartCoordinate = coordinates.first,
                                            _lineEndCoordinate = coordinates.second
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
                        if (spotTopDist == spotBottomDist || abs(spotTopDist - spotBottomDist) <= 150f) {
                            // Vertical center
                            val coordinates = getRectLineStarEndOffset(
                                targetOffset = Offset(
                                    x = spotCenter.x,
                                    y = (spotTopDist / 4f) * 3
                                )
                            )

                            TextCoordinate.PortraitHorizontalVerticalCenter(
                                _lineStartCoordinate = coordinates.first,
                                _lineEndCoordinate = coordinates.second
                            )
                        } else {
                            val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)

                            when (longestVerticalSpacing) {

                                spotTopDist -> {
                                    // Portrait Top
                                    val coordinates = getRectLineStarEndOffset(
                                        targetOffset = Offset(
                                            x = (containerSize.width / 2f),
                                            y = (spotTopDist / 2f) + (spotTopDist / 4f)
                                        )
                                    )

                                    TextCoordinate.PortraitHorizontalCenterTop(
                                        _lineStartCoordinate = coordinates.first,
                                        _lineEndCoordinate = coordinates.second
                                    )
                                }

                                else -> {
                                    // Portrait Bottom
                                    val coordinates = getRectLineStarEndOffset(
                                        targetOffset = Offset(
                                            x = (containerSize.width / 2f),
                                            y = (spotBottomDist / 3f)
                                        )
                                    )

                                    TextCoordinate.PortraitHorizontalCenterBottom(
                                        _lineStartCoordinate = coordinates.first,
                                        _lineEndCoordinate = coordinates.second
                                    )
                                }
                            }
                        }
                    } else if (spotTopDist == spotBottomDist) {
                        // Center vertically
                        val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                        when (longestHorizontalSpacing) {

                            spotLeftDist -> {
                                // Portrait Left
                                val coordinates = getRectLineStarEndOffset(
                                    targetOffset = Offset(
                                        x = (containerSize.width / 2f),
                                        y = (containerSize.height / 3f)
                                    )
                                )

                                TextCoordinate.PortraitVerticalCenterLeft(
                                    _lineStartCoordinate = coordinates.first,
                                    _lineEndCoordinate = coordinates.second
                                )
                            }

                            else -> {
                                // Portrait right
                                val coordinates = getRectLineStarEndOffset(
                                    targetOffset = Offset(
                                        x = (containerSize.width / 2f),
                                        y = (containerSize.height / 3f)
                                    )
                                )

                                TextCoordinate.PortraitVerticalCenterRight(
                                    _lineStartCoordinate = coordinates.first,
                                    _lineEndCoordinate = coordinates.second
                                )
                            }
                        }
                    } else {

                        val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)
                        val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                        when (longestHorizontalSpacing) {

                            spotLeftDist -> {
                                // Portrait left
                                when (longestVerticalSpacing) {

                                    spotTopDist -> {
                                        // Portrait top
                                        val coordinates = getRectLineStarEndOffset(
                                            targetOffset = Offset(
                                                x = containerSize.width / 2f,
                                                y = spotTopDist / 2f + (spotTopDist / 4f)
                                            )
                                        )

                                        TextCoordinate.PortraitLeftTop(
                                            _lineStartCoordinate = coordinates.first,
                                            _lineEndCoordinate = coordinates.second
                                        )
                                    }

                                    spotBottomDist -> {
                                        // Portrait bottom
                                        val coordinates = getRectLineStarEndOffset(
                                            targetOffset = Offset(
                                                x = containerSize.width / 2f,
                                                y = spotBottom + (spotBottomDist / 3f)
                                            )
                                        )

                                        TextCoordinate.PortraitLeftBottom(
                                            _lineStartCoordinate = coordinates.first,
                                            _lineEndCoordinate = coordinates.second
                                        )
                                    }

                                    else -> TextCoordinate.None
                                }
                            }

                            else -> {
                                // Portrait right
                                when (longestVerticalSpacing) {

                                    spotTopDist -> {
                                        // Portrait top
                                        val coordinates = getRectLineStarEndOffset(
                                            targetOffset = Offset(
                                                x = (containerSize.width / 2f),
                                                y = (containerSize.height / 2f) + (spotTopDist / 4f)
                                            )
                                        )

                                        TextCoordinate.PortraitRightTop(
                                            _lineStartCoordinate = coordinates.first,
                                            _lineEndCoordinate = coordinates.second
                                        )
                                    }

                                    spotBottomDist -> {
                                        // Portrait bottom
                                        val coordinates = getRectLineStarEndOffset(
                                            targetOffset = Offset(
                                                x = (containerSize.width / 2f),
                                                y = spotBottom + (spotBottomDist / 3f)
                                            )
                                        )

                                        TextCoordinate.PortraitRightBottom(
                                            _lineStartCoordinate = coordinates.first,
                                            _lineEndCoordinate = coordinates.second
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
    }
    private fun getRectLineStarEndOffset(targetOffset: Offset): Pair<Offset, Offset> {

        val dx = targetOffset.x - spotCenter.x
        val dy = targetOffset.y - spotCenter.y

        // Half dimensions
        val halfW = spotWidth / 2f
        val halfH = spotHeight / 2f

        // Calculate scale factor to reach each edge
        val scaleX = if (dx != 0f) halfW / abs(dx) else Float.MAX_VALUE
        val scaleY = if (dy != 0f) halfH / abs(dy) else Float.MAX_VALUE

        // The smallest scale hits the edge first
        val scale = minOf(scaleX, scaleY)


        return Pair(
            Offset(
                x = spotCenter.x + dx * scale + if (dx > 0) padding else -padding,
                y = spotCenter.y + dy * scale + if (dy > 0) padding else -padding
            ),
            targetOffset
        )
    }

    private fun getCircleLineStarEndOffset(target: Offset): Pair<Offset, Offset> {

        val dx = target.x - spotCenter.x
        val dy = target.y - spotCenter.y
        val distance = sqrt(dx * dx + dy * dy)

        return Pair(
            target,
            Offset(
                x = spotCenter.x + (dx / distance) * (radius + padding),
                y = spotCenter.y + (dy / distance) * (radius + padding)
            )
        )
    }

    private fun getScreenOrientation(): Int {
        val configuration = context.resources.configuration
        return configuration.orientation
    }
}