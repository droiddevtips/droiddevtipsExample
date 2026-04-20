package com.droiddevtips.spotlight.spotlightFeature.coordinateCalculator

import kotlin.reflect.KProperty

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
interface ReadOnlyProperty<in R, out T> {
    operator fun getValue(thisRef: R, property: KProperty<*>):T
}