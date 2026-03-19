package com.droiddevtips.pinchtozoom

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */

@Composable
fun ZoomableBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val scope = rememberCoroutineScope()

    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy),
        label = "ScaleAnimation"
    )
    val animatedOffset by animateOffsetAsState(
        targetValue = offset,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "OffsetAnimation"
    )

    BoxWithConstraints(
        modifier = modifier.clip(RectangleShape)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                    translationX = animatedOffset.x
                    translationY = animatedOffset.y
                }
                .pointerInput(Unit) {
                    detectTransformGestures(panZoomLock = true) { _, pan, zoom, _ ->

                        val newScale = (scale * zoom).coerceIn(1f, 5f)
                        val scaledPan = pan * newScale

                        val extraWidth = (newScale - 1) * constraints.maxWidth
                        val extraHeight = (newScale - 1) * constraints.maxHeight

                        val maxX = extraWidth / 2f
                        val maxY = extraHeight / 2f

                        scale = newScale
                        offset = Offset(
                            x = (offset.x + scaledPan.x).coerceIn(-maxX, maxX),
                            y = (offset.y + scaledPan.y).coerceIn(-maxY, maxY)
                        )
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures (
                        onDoubleTap = {
                            scope.launch {
                                scale = 1f
                                offset = Offset.Zero
                            }
                        }
                    )
                }
        ) {
            content()
        }
    }
}