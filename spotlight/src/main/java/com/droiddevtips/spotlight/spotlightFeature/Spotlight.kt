package com.droiddevtips.spotlight.spotlightFeature

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun Spotlight(
    spotLightInfo: SpotlightInfo?,
    onDismiss: () -> Unit
) {

    if (spotLightInfo == null)
        return

    val scrimAlpha by animateFloatAsState(
        targetValue = 0.85f,
        animationSpec = tween(350),
        label = "scrim"
    )

    // Pulsing ring on the spotlight edge.
    // Not read with `by` — keep it as State<Float> so the value is only
    // consumed inside the Canvas draw phase, preventing recomposition on
    // every animation frame.
    val infiniteTransition = rememberInfiniteTransition(label = "ring")
    val ringPulseState = infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "ringPulse"
    )

    val lineProgress = remember { Animatable(0f) }

    when (spotLightInfo.type) {
        is SpotlightType.Circle -> CircleSpotlight(
            scrimAlpha = scrimAlpha,
            lineProgress = lineProgress,
            ringPulse = { ringPulseState.value }, // Lambda to prevent infinite recomposition
            sportLightInfo = spotLightInfo,
            onDismiss = onDismiss
        )

        is SpotlightType.Rect -> RectSpotlight(
            ringPulse = { ringPulseState.value }, // Lambda to prevent infinite recomposition
            lineProgress = lineProgress,
            scrimAlpha = scrimAlpha,
            sportLightInfo =  spotLightInfo,
            onDismiss = onDismiss
        )
    }


}