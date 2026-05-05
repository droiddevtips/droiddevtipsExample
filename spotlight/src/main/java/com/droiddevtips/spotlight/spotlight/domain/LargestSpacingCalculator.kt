package com.droiddevtips.spotlight.spotlight.domain

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import com.droiddevtips.spotlight.delegates.ReadOnlyProperty
import com.droiddevtips.spotlight.spotlight.data.TextCoordinate
import com.droiddevtips.spotlight.spotlight.data.SpotlightType
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

    private var spotCenter: Offset = Offset.Companion.Zero
    private var spotWidth: Float = 0f
    private var spotHeight: Float = 0f
    private var radius: Float = 20f
    private val padding: Float = 20f

    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): TextCoordinate {

        //val padding = 20f
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

        Log.i("TAG56", "Top -> $spotTopDist")
        Log.i("TAG56", "bottom -> $spotBottomDist")

        return when (spotlightType) {

            is SpotlightType.Circle -> {
                /// -------------------
                when (orientation) {

                    //TODO: Calculate the line start coordinate as well and test it on table as well

                    Configuration.ORIENTATION_LANDSCAPE -> {
                        if (spotLeftDist == spotRightDist || abs(spotLeftDist - spotRightDist) <= 150f) {
                            // Horizontal center
                            Log.i("TAG45", "Landscape Horizontal center")

                            if (spotTopDist == spotBottomDist || abs(spotTopDist - spotBottomDist) <= 150f) {
                                // Vertical center
                                Log.i("TAG45", "Landscape Vertical center")

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
                                        Log.i("TAG45", "Landscape Top")

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
                                        Log.i("TAG45", "Landscape Bottom")
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
                            Log.i("TAG45", "Landscape Center vertically")
                            val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                            when (longestHorizontalSpacing) {

                                spotLeftDist -> {
                                    Log.i("TAG45", "Landscape Left")

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
                                    Log.i("TAG45", "Landscape Right")
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
                                    Log.i("TAG45", "Landscape Left")
                                    when (longestVerticalSpacing) {

                                        spotTopDist -> {
                                            Log.i("TAG45", "Landscape vertical Top")
                                            Log.i("TAG45", "Top -> $spotTopDist")
                                            Log.i("TAG45", "Bottom -> $spotBottomDist")
                                            Log.i("TAG45", "Landscape vertical Top")

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
                                            Log.i("TAG45", "Landscape vertical Bottom")
                                            Log.i("TAG45", "Dis spot top -> ${spotTopDist}")
                                            Log.i("TAG45", "Dis spot bottom -> ${spotBottomDist}")
                                            Log.i("TAG45", "Landscape vertical Bottom")

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
                                    Log.i("TAG45", "Landscape Right")
                                    when (longestVerticalSpacing) {

                                        spotTopDist -> {
                                            Log.i("TAG45", "Landscape vertical Top")
                                            Log.i("TAG45", "Top -> $spotTopDist")
                                            Log.i("TAG45", "Bottom -> $spotBottomDist")
                                            Log.i("TAG45", "Landscape vertical Top")


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
                                            Log.i("TAG45", "Landscape vertical Bottom")

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
                            Log.i("TAG49", "Portrait Horizontal center")
                            if (spotTopDist == spotBottomDist || abs(spotTopDist - spotBottomDist) <= 150f) {
                                // Vertical center
                                Log.i("TAG49", "Portrait Vertical center")

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
                                        Log.i("TAG49", "Portrait Top")

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
                                        Log.i("TAG49", "Portrait Bottom")

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
                            Log.i("TAG49", "Portrait Center vertically")
                            val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                            when (longestHorizontalSpacing) {

                                spotLeftDist -> {
                                    Log.i("TAG49", "Portrait Left")

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
                                    Log.i("TAG49", "Portrait right")

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
                                    Log.i("TAG49", "Portrait left")
                                    when (longestVerticalSpacing) {

                                        spotTopDist -> {
                                            Log.i("TAG49", "Portrait top")
                                            Log.i(
                                                "TAG49",
                                                "Container height -> ${containerSize.height}"
                                            )
                                            Log.i(
                                                "TAG49",
                                                "Container width -> ${containerSize.width}"
                                            )

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
                                            Log.i("TAG49", "Portrait bottom")

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
                                    Log.i("TAG49", "Portrait right")
                                    when (longestVerticalSpacing) {

                                        spotTopDist -> {
                                            Log.i("TAG49", "Portrait top")

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
                                            Log.i("TAG49", "Portrait bottom")

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
            }

            is SpotlightType.Rect -> when (orientation) {

                //TODO: Calculate the line start coordinate as well and test it on table as well

                Configuration.ORIENTATION_LANDSCAPE -> {
                    if (spotLeftDist == spotRightDist || abs(spotLeftDist - spotRightDist) <= 150f) {
                        // Horizontal center
                        Log.i("TAG56", "Landscape Horizontal center")

                        if (spotTopDist == spotBottomDist || abs(spotTopDist - spotBottomDist) <= 150f) {
                            // Vertical center
                            Log.i("TAG56", "Landscape Vertical center")

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
                                    Log.i("TAG56", "Landscape Top")
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
                                    Log.i("TAG56", "Landscape Bottom")

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
                        Log.i("TAG56", "Landscape Center vertically")
                        val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                        when (longestHorizontalSpacing) {

                            spotLeftDist -> {
                                Log.i("TAG56", "Landscape Left")

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
                                Log.i("TAG56", "Landscape Right")
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
                                Log.i("TAG56", "Landscape Left")
                                when (longestVerticalSpacing) {

                                    spotTopDist -> {
                                        Log.i("TAG56", "Landscape vertical Top")
                                        Log.i("TAG56", "Top -> $spotTopDist")
                                        Log.i("TAG56", "Bottom -> $spotBottomDist")
                                        Log.i("TAG56", "Landscape vertical Top")

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
                                        Log.i("TAG56", "Landscape vertical Bottom")
                                        Log.i("TAG56", "Dis spot top -> ${spotTopDist}")
                                        Log.i("TAG56", "Dis spot bottom -> ${spotBottomDist}")
                                        Log.i("TAG56", "Landscape vertical Bottom")

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
                                Log.i("TAG56", "Landscape Right")
                                when (longestVerticalSpacing) {

                                    spotTopDist -> {
                                        Log.i("TAG56", "Landscape vertical Top")

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
                                        Log.i("TAG56", "Landscape vertical Bottom")

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
                        Log.i("TAG56", "Portrait Horizontal center")
                        if (spotTopDist == spotBottomDist || abs(spotTopDist - spotBottomDist) <= 150f) {
                            // Vertical center
                            Log.i("TAG56", "Portrait Vertical center")

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
                                    Log.i("TAG56", "Portrait Top")

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
                                    Log.i("TAG56", "Portrait Bottom")

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
                        Log.i("TAG56", "Portrait Center vertically")
                        val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                        when (longestHorizontalSpacing) {

                            spotLeftDist -> {
                                Log.i("TAG56", "Portrait Left")

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
                                Log.i("TAG56", "Portrait right")
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
                                Log.i("TAG56", "Portrait left")
                                when (longestVerticalSpacing) {

                                    spotTopDist -> {
                                        Log.i("TAG56", "Portrait top")

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
                                        Log.i("TAG56", "Portrait bottom")

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
                                Log.i("TAG56", "Portrait right")
                                when (longestVerticalSpacing) {

                                    spotTopDist -> {
                                        Log.i("TAG56", "Portrait top")

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
                                        Log.i("TAG56", "Portrait bottom")

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

        return when (orientation) {

            //TODO: Calculate the line start coordinate as well and test it on table as well

            Configuration.ORIENTATION_LANDSCAPE -> {
                if (spotLeftDist == spotRightDist || abs(spotLeftDist - spotRightDist) <= 150f) {
                    // Horizontal center
                    Log.i("TAG56", "Landscape Horizontal center")

                    if (spotTopDist == spotBottomDist || abs(spotTopDist - spotBottomDist) <= 150f) {
                        // Vertical center
                        Log.i("TAG56", "Landscape Vertical center")

                        TextCoordinate.LandscapeHorizontalVerticalCenter(
                            _lineStartCoordinate = Offset(
                                x = (containerSize.width / 2f),
                                y = spotTop - padding
                            ),
                            _lineEndCoordinate = Offset(
                                x = (containerSize.width / 2f),
                                y = (spotTopDist / 2f)
                            )
                        )
                    } else {
                        val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)

                        when (longestVerticalSpacing) {

                            spotTopDist -> {
                                Log.i("TAG56", "Landscape Top")
                                TextCoordinate.LandscapeHorizontalCenterTop(
                                    _lineStartCoordinate = Offset(
                                        x = (containerSize.width / 2f),
                                        y = spotTopDist - padding
                                    ),
                                    _lineEndCoordinate = Offset(
                                        x = (containerSize.width / 2f),
                                        y = (spotTopDist / 2f)
                                    )
                                )
                            }

                            else -> {
                                Log.i("TAG56", "Landscape Bottom")
                                TextCoordinate.LandscapeHorizontalCenterBottom(
                                    _lineStartCoordinate = Offset(
                                        x = (containerSize.width / 2f),
                                        y = spotBottom + padding
                                    ),
                                    _lineEndCoordinate = Offset(
                                        x = (containerSize.width / 2f),
                                        y = (spotBottomDist / 2f)
                                    )
                                )
                            }
                        }
                    }
                } else if (spotTopDist == spotBottomDist) {
                    // Center vertically
                    Log.i("TAG56", "Landscape Center vertically")
                    val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                    when (longestHorizontalSpacing) {

                        spotLeftDist -> {
                            Log.i("TAG56", "Landscape Left")

                            TextCoordinate.LandscapeVerticalCenterLeft(
                                _lineStartCoordinate = Offset(
                                    x = spotLeft - padding,
                                    y = spotCenterY
                                ),
                                _lineEndCoordinate = Offset(
                                    x = (spotLeftDist / 3f) * 2,
                                    y = (containerSize.height / 3f)
                                )
                            )
                        }

                        else -> {
                            Log.i("TAG56", "Landscape Right")
                            val horizontalCoordinate =
                                ((containerSize.width - spotRightDist) + (spotRightDist / 2f))

                            TextCoordinate.LandscapeVerticalCenterRight(
                                _lineStartCoordinate = Offset(
                                    x = spotRight + padding,
                                    y = spotCenterY
                                ),
                                _lineEndCoordinate = Offset(
                                    x = horizontalCoordinate / 2f,
                                    y = (containerSize.height / 3f)
                                )
                            )
                        }
                    }
                } else {

                    val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)
                    val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                    when (longestHorizontalSpacing) {

                        spotLeftDist -> {
                            Log.i("TAG56", "Landscape Left")
                            when (longestVerticalSpacing) {

                                spotTopDist -> {
                                    Log.i("TAG56", "Landscape vertical Top")
                                    Log.i("TAG56", "Top -> $spotTopDist")
                                    Log.i("TAG56", "Bottom -> $spotBottomDist")
                                    Log.i("TAG56", "Landscape vertical Top")

                                    TextCoordinate.LandscapeLeftTop(
                                        _lineStartCoordinate = Offset(
                                            x = spotLeft - padding,
                                            y = spotTop + (abs(spotTop - spotBottom) / 2f)
                                        ),
                                        _lineEndCoordinate = Offset(
                                            x = (containerSize.width / 2f),
                                            y = spotTopDist / 2f
                                        )
                                    )
                                }

                                spotBottomDist -> {
                                    Log.i("TAG56", "Landscape vertical Bottom")
                                    Log.i("TAG56", "Dis spot top -> ${spotTopDist}")
                                    Log.i("TAG56", "Dis spot bottom -> ${spotBottomDist}")
                                    Log.i("TAG56", "Landscape vertical Bottom")

                                    TextCoordinate.LandscapeLeftBottom(
                                        _lineStartCoordinate = Offset(
                                            x = spotLeft - padding,
                                            y = spotTop + (abs(spotTop - spotBottom) / 2f)
                                        ),
                                        _lineEndCoordinate = Offset(
                                            x = (containerSize.width / 3f) * 2,
                                            y = spotTopDist + (spotBottomDist / 2f)
                                        )
                                    )
                                }

                                else -> TextCoordinate.None
                            }
                        }

                        else -> {
                            Log.i("TAG56", "Landscape Right")
                            when (longestVerticalSpacing) {

                                spotTopDist -> {
                                    Log.i("TAG56", "Landscape vertical Top")
                                    Log.i("TAG56", "Top -> $spotTopDist")
                                    Log.i("TAG56", "Bottom -> $spotBottomDist")
                                    Log.i("TAG56", "Landscape vertical Top")
                                    TextCoordinate.LandscapeRightTop(
                                        _lineStartCoordinate = Offset(
                                            x = spotRight + padding,
                                            y = spotTop + (abs(spotTop - spotBottom) / 2f)
                                        ),
                                        _lineEndCoordinate = Offset(
                                            x = (containerSize.width / 2f),
                                            y = spotTopDist / 2f
                                        )
                                    )
                                }

                                spotBottomDist -> {
                                    Log.i("TAG56", "Landscape vertical Bottom")

                                    TextCoordinate.LandscapeRightBottom(
                                        _lineStartCoordinate = Offset(
                                            x = spotRight + padding,
                                            y = spotTopDist + abs(spotTop - spotBottom) / 2f
                                        ),
                                        _lineEndCoordinate = Offset(
                                            x = (containerSize.width / 3f),
                                            y = spotTopDist + (spotBottomDist / 2f)
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
                    Log.i("TAG56", "Portrait Horizontal center")
                    if (spotTopDist == spotBottomDist || abs(spotTopDist - spotBottomDist) <= 150f) {
                        // Vertical center
                        Log.i("TAG56", "Portrait Vertical center")

                        TextCoordinate.PortraitHorizontalVerticalCenter(
                            _lineStartCoordinate = Offset(
                                x = spotCenterX,
                                y = spotTop - padding
                            ),
                            _lineEndCoordinate = Offset(
                                x = (containerSize.width / 2f),
                                y = (spotTopDist / 2f) + (spotTopDist / 4f)
                            )
                        )
                    } else {
                        val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)

                        when (longestVerticalSpacing) {

                            spotTopDist -> {
                                Log.i("TAG56", "Portrait Top")

                                TextCoordinate.PortraitHorizontalCenterTop(
                                    _lineStartCoordinate = Offset(
                                        x = spotCenterX,
                                        y = spotTop - padding
                                    ),
                                    _lineEndCoordinate = Offset(
                                        x = (containerSize.width / 2f),
                                        y = (spotTopDist / 2f) + (spotTopDist / 4f)
                                    )
                                )
                            }

                            else -> {
                                Log.i("TAG56", "Portrait Bottom")

                                TextCoordinate.PortraitHorizontalCenterBottom(
                                    _lineStartCoordinate = Offset(
                                        x = spotCenterX,
                                        y = spotBottom + padding
                                    ),
                                    _lineEndCoordinate = Offset(
                                        x = (containerSize.width / 2f),
                                        y = (spotBottomDist / 3f)
                                    )
                                )
                            }
                        }
                    }
                } else if (spotTopDist == spotBottomDist) {
                    // Center vertically
                    Log.i("TAG56", "Portrait Center vertically")
                    val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                    when (longestHorizontalSpacing) {

                        spotLeftDist -> {
                            Log.i("TAG56", "Portrait Left")

                            TextCoordinate.PortraitVerticalCenterLeft(
                                _lineStartCoordinate = Offset(
                                    x = spotCenterX,
                                    y = spotTop - padding
                                ),
                                _lineEndCoordinate = Offset(
                                    x = (containerSize.width / 2f),
                                    y = (containerSize.height / 3f)
                                )
                            )
                        }

                        else -> {
                            Log.i("TAG56", "Portrait right")
                            TextCoordinate.PortraitVerticalCenterRight(
                                _lineStartCoordinate = Offset(
                                    x = spotCenterX,
                                    y = spotTop - padding
                                ),
                                _lineEndCoordinate = Offset(
                                    x = (containerSize.width / 2f),
                                    y = (containerSize.height / 3f)
                                )
                            )
                        }
                    }
                } else {

                    val longestVerticalSpacing = maxOf(spotTopDist, spotBottomDist)
                    val longestHorizontalSpacing = maxOf(spotLeftDist, spotRightDist)
                    when (longestHorizontalSpacing) {

                        spotLeftDist -> {
                            Log.i("TAG56", "Portrait left")
                            when (longestVerticalSpacing) {

                                spotTopDist -> {
                                    Log.i("TAG56", "Portrait top")

                                    TextCoordinate.PortraitLeftTop(
                                        _lineStartCoordinate = Offset(
                                            x = spotCenterX,
                                            y = spotTop - padding
                                        ),
                                        _lineEndCoordinate = Offset(
                                            x = containerSize.width / 2f,
                                            y = spotTopDist / 2f + (spotTopDist / 4f)
                                        )
                                    )
                                }

                                spotBottomDist -> {
                                    Log.i("TAG56", "Portrait bottom")

                                    TextCoordinate.PortraitLeftBottom(
                                        _lineStartCoordinate = Offset(
                                            x = spotCenterX,
                                            y = spotBottom + padding
                                        ),
                                        _lineEndCoordinate = Offset(
                                            x = containerSize.width / 2f,
                                            y = spotBottom + (spotBottomDist / 3f)
                                        )
                                    )
                                }

                                else -> TextCoordinate.None
                            }
                        }

                        else -> {
                            Log.i("TAG56", "Portrait right")
                            when (longestVerticalSpacing) {

                                spotTopDist -> {
                                    Log.i("TAG56", "Portrait top")

                                    TextCoordinate.PortraitRightTop(
                                        _lineStartCoordinate = Offset(
                                            x = spotCenterX,
                                            y = spotTop - padding
                                        ),
                                        _lineEndCoordinate = Offset(
                                            x = (containerSize.width / 2f),
                                            y = (containerSize.height / 2f) + (spotTopDist / 4f)
                                        )
                                    )
                                }

                                spotBottomDist -> {
                                    Log.i("TAG56", "Portrait bottom")

                                    TextCoordinate.PortraitRightBottom(
                                        _lineStartCoordinate = Offset(
                                            x = spotCenterX,
                                            y = spotBottom + padding
                                        ),
                                        _lineEndCoordinate = Offset(
                                            x = (containerSize.width / 2f),
                                            y = spotBottom + (spotBottomDist / 3f)
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

    /*

    val center = Offset(rect.center.x, rect.center.y)
val target = Offset(targetX, targetY)

val dx = target.x - center.x
val dy = target.y - center.y

// Half dimensions
val halfW = rect.width / 2f
val halfH = rect.height / 2f

// Calculate scale factor to reach each edge
val scaleX = if (dx != 0f) halfW / abs(dx) else Float.MAX_VALUE
val scaleY = if (dy != 0f) halfH / abs(dy) else Float.MAX_VALUE

// The smallest scale hits the edge first
val scale = minOf(scaleX, scaleY)

val padding = 8f

val startOffset = Offset(
    x = center.x + dx * scale + if (dx > 0) padding else -padding,
    y = center.y + dy * scale + if (dy > 0) padding else -padding
)

    */

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