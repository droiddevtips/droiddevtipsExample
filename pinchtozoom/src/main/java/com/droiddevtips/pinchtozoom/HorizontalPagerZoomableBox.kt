package com.droiddevtips.pinchtozoom

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroidSize
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.abs

/**
 * The zoomable box composable to be used inside a horizontal pager
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun HorizontalPagerZoomableBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    onScaleChanged: (Boolean) -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var isGesturing by remember { mutableStateOf(false) }

    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = if (isGesturing) snap()
        else spring(dampingRatio = Spring.DampingRatioNoBouncy),
        label = "ScaleAnimation"
    )
    val animatedOffset by animateOffsetAsState(
        targetValue = offset,
        animationSpec = if (isGesturing) snap()
        else spring(stiffness = Spring.StiffnessLow),
        label = "OffsetAnimation"
    )

    BoxWithConstraints(
        modifier = modifier
            .statusBarsPadding()
            .padding(top = 44.dp)
            .navigationBarsPadding()
            .clip(RectangleShape),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                    translationX = animatedOffset.x
                    translationY = animatedOffset.y
                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = { tapOffset ->
                            Log.i("TAG12","Tap offset -> $tapOffset")
                            if (scale > 1f) {
                                // Already zoomed in -> reset
                                scale = 1f
                                offset = Offset.Zero
                                onScaleChanged(false)
                            } else {
                                // Zoom in toward the point that was tapped
                                val targetScale = 2.5f
                                val boxCenter = Offset(size.width / 2f, size.height / 2f)

                                // Translate so the tapped point stays under the finger
                                val rawOffset = (boxCenter - tapOffset) * (targetScale - 1f)

                                val maxX = (targetScale - 1f) * size.width / 2f
                                val maxY = (targetScale - 1f) * size.height / 2f

                                scale = targetScale
                                offset = Offset(
                                    x = rawOffset.x.coerceIn(-maxX, maxX),
                                    y = rawOffset.y.coerceIn(-maxY, maxY)
                                )
                                onScaleChanged(true)
                            }
                        }
                    )
                }
                .pointerInput(Unit) {
                    awaitEachGesture {
                        awaitFirstDown(requireUnconsumed = false)
                        isGesturing = true

                        try {
                            var zoom = 1f
                            var pan = Offset.Zero
                            var pastTouchSlop = false
                            val touchSlop = viewConfiguration.touchSlop
                            var isMultiTouch = false

                            do {
                                val event = awaitPointerEvent()
                                if (event.changes.any { it.isConsumed }) break

                                isMultiTouch = event.changes.count { it.pressed } > 1

                                if (isMultiTouch) {
                                    val zoomChange = event.calculateZoom()
                                    val panChange = event.calculatePan()

                                    if (!pastTouchSlop) {
                                        zoom *= zoomChange
                                        pan += panChange
                                        val centroidSize =
                                            event.calculateCentroidSize(useCurrent = false)
                                        val zoomMotion = abs(1 - zoom) * centroidSize
                                        val panMotion = pan.getDistance()
                                        if (zoomMotion > touchSlop || panMotion > touchSlop) {
                                            pastTouchSlop = true
                                        }
                                    }

                                    if (pastTouchSlop) {
                                        if (zoomChange != 1f || panChange != Offset.Zero) {
                                            event.changes.forEach { it.consume() }

                                            val newScale = (scale * zoomChange).coerceIn(1f, 5f)
                                            val scaledPan = panChange * newScale

                                            val extraWidth = (newScale - 1) * constraints.maxWidth
                                            val extraHeight = (newScale - 1) * constraints.maxHeight
                                            val maxX = extraWidth / 2f
                                            val maxY = extraHeight / 2f

                                            scale = newScale
                                            offset = Offset(
                                                x = (offset.x + scaledPan.x).coerceIn(-maxX, maxX),
                                                y = (offset.y + scaledPan.y).coerceIn(-maxY, maxY)
                                            )
                                            onScaleChanged(newScale > 1f)
                                        }
                                    }
                                } else if (scale > 1f) {
                                    val panChange = event.calculatePan()

                                    if (!pastTouchSlop) {
                                        pan += panChange
                                        if (pan.getDistance() > touchSlop) {
                                            pastTouchSlop = true
                                        }
                                    }

                                    if (pastTouchSlop && panChange != Offset.Zero) {
                                        event.changes.forEach { it.consume() }

                                        val extraWidth = (scale - 1) * constraints.maxWidth
                                        val extraHeight = (scale - 1) * constraints.maxHeight
                                        val maxX = extraWidth / 2f
                                        val maxY = extraHeight / 2f

                                        offset = Offset(
                                            x = (offset.x + panChange.x).coerceIn(-maxX, maxX),
                                            y = (offset.y + panChange.y).coerceIn(-maxY, maxY)
                                        )
                                    }
                                }
                            } while (event.changes.any { it.pressed })
                        }finally {
                            isGesturing = false
                        }
                    }
                },
            contentAlignment = contentAlignment
        ) {
            content(this)
        }
    }
}