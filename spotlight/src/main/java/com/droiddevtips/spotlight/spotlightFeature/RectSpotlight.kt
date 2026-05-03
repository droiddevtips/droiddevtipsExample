package com.droiddevtips.spotlight.spotlightFeature

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.spotlight.spotlightFeature.coordinateCalculator.CoordinateCalculator
import com.droiddevtips.spotlight.spotlightFeature.coordinateCalculator.TextCoordinateCalculator
import kotlinx.coroutines.delay

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */

fun Context.pxToDp(px: Float): Float {
    return px / resources.displayMetrics.density
}

@Composable
fun RectSpotlight(
    scrimAlpha: Float,
    ringPulse: Float,
    lineProgress: Animatable<Float, AnimationVector1D>,
    sportLightInfo: SpotlightInfo,
    onDismiss: () -> Unit
) {

    val rectProperty = sportLightInfo.type as SpotlightType.Rect
    val context = LocalContext.current
    val density = LocalDensity.current
    val orientation = LocalConfiguration.current.orientation

    // Always track the overlay's own position in root coordinates, even while the
    // overlay is invisible.  This must live OUTSIDE the scrimAlpha guard so the
    // value is ready (non-zero when inside a Scaffold / inset container) before
    // the first visible frame renders — otherwise the spotlight is misaligned on
    // the first appearance.

    var overlayRootOffset by remember { mutableStateOf(Offset.Zero) }
    var displayCoordinates by remember { mutableStateOf(DisplayCoordinates()) }
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var showEndDot by remember { mutableStateOf(false) }
    val textMeasurer = rememberTextMeasurer()

    Box(
        Modifier
            .fillMaxSize()
            .background(color = rectProperty.overlayColor.copy(alpha = 0.45f))
            .onGloballyPositioned { coords ->
                overlayRootOffset = coords.positionInRoot()
                if (containerSize != IntSize.Zero) {
                    displayCoordinates = CoordinateCalculator.calculate(
                        context = context,
                        density = density,
                        spotlightInfo = sportLightInfo,
                        containerSize = containerSize,
                        spotlightType = SpotlightType.Rect(),
                        overlayRootOffset = coords.positionInRoot()
                    )
                }
            }
            .onSizeChanged {
                containerSize = it
                if (overlayRootOffset != Offset.Zero) {
                    displayCoordinates = CoordinateCalculator.calculate(
                        context = context,
                        density = density,
                        spotlightInfo = sportLightInfo,
                        containerSize = it,
                        spotlightType = SpotlightType.Rect(),
                        overlayRootOffset = overlayRootOffset
                    )
                }
            }
    )

    if (scrimAlpha > 0f) {
        Box(modifier = Modifier.fillMaxSize()) {

            // Convert boundsInRoot() → canvas-local by subtracting the overlay's own
            // root offset. When the overlay is at (0,0) this is a no-op; when it is
            // shifted (e.g. by status-bar insets above the composable), it corrects
            // the mismatch so the spotlight lands exactly on the target.
            Log.i("TAG45", "Overlay root offset 23: $overlayRootOffset")

            Box(
                Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onDismiss() }
            )

            // ── Black scrim with transparent circular hole ────────────────────
            //
            //   Rendered to an off-screen buffer (CompositingStrategy.Offscreen) so
            //   BlendMode.Clear can punch a true alpha hole through the black rectangle,
            //   revealing the composable content underneath the Canvas layer.
            // ────────────────────────────────────────────────────────────────

//            The two canvases are separated because of BlendMode.Clear — that's the key constraint.
//
//            First Canvas (with graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }):
//            - Draws the black scrim
//                    - Punches a transparent hole using BlendMode.Clear
//                    - BlendMode.Clear only works correctly inside an offscreen buffer. Without it, Clear would erase pixels on the actual screen rather than just within the layer, producing wrong results.
//
//            Second Canvas (plain, no offscreen layer):
//            - Draws the pulsing white border ring and the animated line
//            - These use normal BlendMode.SrcOver (the default) — no offscreen buffer needed
//            - If you put these inside the offscreen layer, BlendMode.Clear from the hole would interact with them incorrectly, potentially erasing parts of the ring/line too
//
//            In short: the hole-punching technique requires isolation in its own offscreen layer, and anything drawn after it that shouldn't be affected by Clear must live in a separate Canvas on top.

            Canvas(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            ) {
                // Black scrim
                drawRect(rectProperty.overlayColor.copy(alpha = scrimAlpha))

                // Transparent rounded-rect hole at the component's position

                drawRoundRect(
                    color = Color.Transparent,
                    topLeft = Offset(
                        displayCoordinates.spotlightLeft,
                        displayCoordinates.spotlightTop
                    ),
                    size = Size(
                        displayCoordinates.spotlightRight - displayCoordinates.spotlightLeft,
                        displayCoordinates.spotlightBottom - displayCoordinates.spotlightTop
                    ),
                    cornerRadius = CornerRadius(with(density) { rectProperty.radius.dp.toPx() }),
                    blendMode = BlendMode.Clear
                )

                val lineEndProgress = lerp(
                    start = displayCoordinates.textCoordinate.lineStartCoordinate,
                    stop = displayCoordinates.textCoordinate.lineEndCoordinate,
                    fraction = lineProgress.value
                )
                drawLine(
                    color = Color.White,
                    start = displayCoordinates.textCoordinate.lineStartCoordinate,
                    end = lineEndProgress,
                    strokeWidth = 2.dp.toPx()
                )

                if (showEndDot) {
                    drawCircle(
                        Color.White,
                        radius = 4.dp.toPx(),
                        center = displayCoordinates.textCoordinate.lineEndCoordinate
                    )


                    // Draw text

                    val textPortraitPadding = 80.dp.toPx()
                    val textPadding = 125.dp.toPx()
                    val textLayoutResult = textMeasurer.measure(
                        text = sportLightInfo.text,
                        constraints = Constraints(maxWidth = if(orientation == Configuration.ORIENTATION_LANDSCAPE) { (size.width.toInt() / 2) - textPadding.toInt() } else (size.width.toInt() - textPortraitPadding.toInt())),
                        style = androidx.compose.ui.text.TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    val textCoordinate by TextCoordinateCalculator(
                        textAreaCoordinate = displayCoordinates.textCoordinate,
                        textLayout = textLayoutResult
                    )

                    if (textCoordinate != Offset.Zero) {
                        drawText(
                            textLayoutResult = textLayoutResult,
                            alpha = 1.0f,
                            color = Color.White,
                            topLeft = Offset(
                                x = textCoordinate.x,
                                y = textCoordinate.y
                            )
                        )
                    }
                }
            }

            // ── Pulsing ring + animated line ──────────────────────────────────


            Canvas(Modifier.fillMaxSize()) {
                // Pulsing rounded-rect border at the spotlight boundary

                drawRoundRect(
                    color = rectProperty.strokeColor.copy(alpha = scrimAlpha * ringPulse),
                    topLeft = Offset(
                        displayCoordinates.spotlightLeft,
                        displayCoordinates.spotlightTop
                    ),
                    size = Size(
                        displayCoordinates.spotlightRight - displayCoordinates.spotlightLeft,
                        displayCoordinates.spotlightBottom - displayCoordinates.spotlightTop
                    ),
                    cornerRadius = CornerRadius(with(density) { rectProperty.radius.dp.toPx() }),
                    style = Stroke(width = rectProperty.strokeWidth.dp.toPx())
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        lineProgress.snapTo(0f)
        delay(400)
        lineProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(600, easing = FastOutSlowInEasing)
        )
        showEndDot = true
    }

}