package com.droiddevtips.spotlight.delegates

import kotlin.reflect.KProperty

/**
 * The read-only property delegates interface
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
interface ReadOnlyProperty<in R, out T> {
    operator fun getValue(thisRef: R, property: KProperty<*>):T
}