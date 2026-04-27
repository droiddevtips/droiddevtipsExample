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


    override fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): Offset {

        Log.i("TAG23", "\nTextCoordinate -> $textAreaCoordinate\nText layout Result -> $textLayout\n")


        return Offset.Zero

    }
}