package com.droiddevtips.typography.extensions

import android.content.Context

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

fun Int.dpToPx(context: Context): Float = (this * context.resources.displayMetrics.density)