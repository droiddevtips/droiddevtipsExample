package com.droiddevtips.spotlight.spotlightFeature

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.spotlight.spotlightFeature.coordinateCalculator.CoordinateCalculator
import com.droiddevtips.spotlight.spotlightFeature.coordinateCalculator.TextCoordinateCalculator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */

@Composable
fun CircleSpotlight(
    density: Density,
    scrimAlpha: Float,
    lineProgress: Animatable<Float, AnimationVector1D>,
    ringPulse: Float,
    sportLightInfo: SpotlightInfo,
    onDismiss: () -> Unit
) {

    val rectProperty = sportLightInfo.type as SpotlightType.Circle
    val density = LocalDensity.current
    //val configuration = LocalConfiguration.current
    //val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val context = LocalContext.current
    val orientation = LocalConfiguration.current.orientation

    // --- Circle ---- \\
    val animateCircle = remember { mutableStateOf(true) }
//    val showRingPulse = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    // --- Circle ---- \\

    // --- generic ---\\

    // --- generic ---\\

    /*
    LaunchedEffect(sportLightInfo) {
        if (sportLightInfo != null) {
//            showText = false
//            lineProgress.snapTo(0f)
            delay(400)                                          // wait for scrim
//            lineProgress.animateTo(
//                targetValue = 1f,
//                animationSpec = tween(600, easing = FastOutSlowInEasing)
//            )
//            showText = true                                     // Phase 3: show text
        } else {
//            showText = false
//            lineProgress.snapTo(0f)
        }
    }
    */


    // ----- generic ----- \\


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
                        spotlightType = SpotlightType.Circle(),
                        overlayRootOffset = coords.positionInRoot()
                    )
                }
                //displayCoordinates = CoordinateCalculator.calculate(density = density, spotlightInfo = sportLightInfo, overlayRootOffset = coords.positionInRoot())
                Log.i("TAG45", "Overlay root offset: $overlayRootOffset")
            }
            .onSizeChanged {
                containerSize = it
                if (overlayRootOffset != Offset.Zero) {
                    displayCoordinates = CoordinateCalculator.calculate(
                        context = context,
                        density = density,
                        spotlightInfo = sportLightInfo,
                        containerSize = it,
                        spotlightType = SpotlightType.Circle(),
                        overlayRootOffset = overlayRootOffset
                    )
                }
            }
    )

    if (scrimAlpha > 0f) {
        Box(modifier = Modifier.fillMaxSize()) {
//            val screenW = with(density) { configuration.screenWidthDp.dp.toPx() }
//            val screenH = with(density) { configuration.screenHeightDp.dp.toPx() }
//
//            val spotRadius = sportLightInfo.let {
//                val hw = it.bounds.width / 2f
//                val hh = it.bounds.height / 2f
//                sqrt(hw * hw + hh * hh) + with(density) { 36.dp.toPx() }
//            }

            // Convert boundsInRoot() → canvas-local by subtracting the overlay's own
            // root offset. When the overlay is at (0,0) this is a no-op; when it is
            // shifted (e.g. by status-bar insets above the composable), it corrects
            // the mismatch so the spotlight lands exactly on the target.
            Log.i("TAG45", "Overlay root offset 23: $overlayRootOffset")
            /*
            val spotPad =
                with(density) { 10.dp.toPx() } // adds 20dp of breathing room around the target component's bounds on all four sides
            val spotLeft =
                (sportLightInfo.bounds.left) - overlayRootOffset.x - spotPad // canvas-local left edge, then extend 20dp further left (spotlight grows outward)
            val spotTop = (sportLightInfo.bounds.top) - overlayRootOffset.y - spotPad
            //(info?.bounds?.top    ?: 0f) = the top edge of the highlighted component, in root coordinates (or 0 if nothing is highlighted)
            //- overlayRootOffset.y  =  converts from root coordinates to canvas-local coordinates (corrects for any vertical shift of the overlay, e.g. status bar insets)
            //- spotPad   =  extends the spotlight 20dp upward (smaller y = higher on screen) to add breathing room above the component
            val spotRight = (sportLightInfo.bounds.right) - overlayRootOffset.x + spotPad
            val spotBottom = (sportLightInfo.bounds.bottom) - overlayRootOffset.y + spotPad
            val spotCenterX = (spotLeft + spotRight) / 2f
            val spotCenterY = (spotTop + spotBottom) / 2f
            */
            // -----------

            // ----- circle ----- \\
            val maxOf = maxOf(
                (displayCoordinates.spotlightRight - displayCoordinates.spotlightLeft),
                (displayCoordinates.spotlightBottom - displayCoordinates.spotlightTop)
            )
            val circleCenter = Offset(
                displayCoordinates.spotlightItemCenterX,
                displayCoordinates.spotlightItemCenterY
            )

            val horizontal = abs(displayCoordinates.spotlightLeft - displayCoordinates.spotlightRight)
            val vertical = abs(displayCoordinates.spotlightTop - displayCoordinates.spotlightBottom)
            val maxOf2 = maxOf(horizontal, vertical)
            Log.i("TAG78","Horizontal -> $horizontal")
            Log.i("TAG78","Vertical -> $vertical")
            Log.i("TAG78","Max of -> $maxOf2")

            density
            //val test = with(density) { 414f.toDp() }
//            val test = context.pxToDp(maxOf) + (displayCoordinates.spotlightPadding * 2)
            //val test = context.pxToDp(maxOf2) + displayCoordinates.spotlightPadding
            // ----- circle ----- \\
            /*
            // Radius based on text diagonal so circle always fits
        val radius = (sqrt(textWidth.pow(2) + textHeight.pow(2)) / 2f) + 16f
            */
            val test = (sqrt(horizontal.pow(2) + vertical.pow(2)) / 2f)

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

            val circleRadius by animateFloatAsState(
                targetValue = if (animateCircle.value) test else 0f,
                animationSpec = tween(550),
                label = "scrim"
            )

            Canvas(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
            ) {
                // Black scrim
                drawRect(rectProperty.overlayColor.copy(alpha = scrimAlpha))

                // Transparent rounded-rect hole at the component's position

                drawCircle(
                    color = Color.Transparent,
                    radius = circleRadius,
                    center = circleCenter,
                    blendMode = BlendMode.Clear
                )
            }

            // ── Pulsing ring + animated line ──────────────────────────────────


            Canvas(Modifier.fillMaxSize()) {
                // Pulsing rounded-rect border at the spotlight boundary

                drawCircle(
                    color = Color.White.copy(alpha = scrimAlpha * ringPulse),
                    radius = circleRadius,
                    center = circleCenter,
                    style = Stroke(width = 2.dp.toPx())
                )


                val lineEndProgress = lerp(
                    start = displayCoordinates.textCoordinate.lineStartCoordinate,
                    stop = displayCoordinates.textCoordinate.lineEndCoordinate,
                    fraction = lineProgress.value
                )

                Log.i("TAG46","\n\nLine start -> ${displayCoordinates.textCoordinate.lineStartCoordinate}")
                Log.i("TAG46","Line end -> ${displayCoordinates.textCoordinate.lineEndCoordinate}\n\n")

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
                        text = "Most Space Area Most Space Area Most Space Area Most Space Area",
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

                    Log.i("TAG23", "Text coordinate result -> $textCoordinate")

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
        }
    }

    LaunchedEffect(Unit) {
        animateCircle.value = true
        lineProgress.snapTo(0f)
        delay(400)
        lineProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(600, easing = FastOutSlowInEasing)
        )
        showEndDot = true
    }
}