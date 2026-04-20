package com.droiddevtips.spotlight.spotlightFeature

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.droiddevtips.spotlight.spotlightFeature.coordinateCalculator.CoordinateCalculator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    //val configuration = LocalConfiguration.current
    //val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val context = LocalContext.current

    // --- Circle ---- \\
    val animateCircle = remember { mutableStateOf(false) }
    val showRingPulse = remember { mutableStateOf(false) }
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
            val test = context.pxToDp(maxOf) + (displayCoordinates.spotlightPadding * 2)
            // ----- circle ----- \\

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
                finishedListener = {
                    Log.i("TAG32", "Circle finished -> $it")
                    scope.launch {
                        delay(500)
                        showRingPulse.value = true
                    }
                },
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

                if (showRingPulse.value) {
                    drawCircle(
                        color = Color.White.copy(alpha = scrimAlpha * ringPulse),
                        radius = test,
                        center = circleCenter,
                        style = Stroke(width = 2.dp.toPx())
                    )
                }




            }
        }
    }

    LaunchedEffect(Unit) {
        animateCircle.value = true
    }
}