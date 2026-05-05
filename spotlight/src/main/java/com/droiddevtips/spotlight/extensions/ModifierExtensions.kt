package com.droiddevtips.spotlight.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

/**
 * The [Modifier] extensions
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun Modifier.observeSpotlightView(
    id: String,
    reportSpotlight: (String, Rect) -> Unit
): Modifier {

    val reported = rememberSaveable {
        mutableStateOf(false)
    }

    return this.onGloballyPositioned {
        CoroutineScope(Dispatchers.Main).launch {
            delay(2.seconds)
            if (!reported.value) {
                reported.value = true
                reportSpotlight(id, it.boundsInRoot())
            }
        }
    }
}