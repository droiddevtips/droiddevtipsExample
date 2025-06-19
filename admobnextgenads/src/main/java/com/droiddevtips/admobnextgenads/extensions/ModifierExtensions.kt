package com.droiddevtips.admobnextgenads.extensions

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale

/**
 * These are the [androidx.compose.ui.Modifier] extensions
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun Modifier.pulseEffect(
    initialValue: Float = 0.8f,
    targetValue: Float = 1.0f,
    durationInMilli: Int = 1000
): Modifier =
    this.also {

        val pulseTransition = rememberInfiniteTransition()
        val pulseScale by pulseTransition.animateFloat(
            initialValue = initialValue,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(durationInMilli),
                repeatMode = RepeatMode.Reverse
            ),
            label = "PulseScale"
        )

        return Modifier.scale(pulseScale)
    }