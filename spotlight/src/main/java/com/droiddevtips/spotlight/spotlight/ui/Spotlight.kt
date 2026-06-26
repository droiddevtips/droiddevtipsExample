package com.droiddevtips.spotlight.spotlight.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.droiddevtips.spotlight.spotlight.data.SpotlightInfo
import com.droiddevtips.spotlight.spotlight.data.SpotlightType

/**
 * The spotlight composable view
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
        label = "scrimAlphaAnimation"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "shapeBorder")
    val shapeBorderPulseState = infiniteTransition.animateFloat( // <--- instead of val shapeBorderPulseState by infiniteTransition.animateFloat(...
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "shapeBorderPulse"
    )

    val lineProgress = remember { Animatable(0f) }

    when (spotLightInfo.type) {
        is SpotlightType.Circle -> CircleSpotlight(
            scrimAlpha = scrimAlpha,
            lineProgress = lineProgress,
            circleBorderPulse = { shapeBorderPulseState.value }, // Lambda to prevent infinite recomposition
            spotLightInfo = spotLightInfo,
            onDismiss = onDismiss
        )

        is SpotlightType.Rect -> RectSpotlight(
            rectBorderPulse = { shapeBorderPulseState.value }, // Lambda to prevent infinite recomposition
            lineProgress = lineProgress,
            scrimAlpha = scrimAlpha,
            sportLightInfo = spotLightInfo,
            onDismiss = onDismiss
        )
    }
}