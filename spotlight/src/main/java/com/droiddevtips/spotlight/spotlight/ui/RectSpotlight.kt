package com.droiddevtips.spotlight.spotlight.ui

import android.content.res.Configuration
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.spotlight.spotlight.data.DisplayCoordinates
import com.droiddevtips.spotlight.spotlight.data.SpotlightInfo
import com.droiddevtips.spotlight.spotlight.data.SpotlightType
import com.droiddevtips.spotlight.spotlight.domain.CoordinateCalculator
import com.droiddevtips.spotlight.spotlight.domain.TextCoordinateCalculator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

/**
 * The rect spotlight composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun RectSpotlight(
    scrimAlpha: Float,
    rectBorderPulse: () -> Float,
    lineProgress: Animatable<Float, AnimationVector1D>,
    sportLightInfo: SpotlightInfo,
    onDismiss: () -> Unit
) {

    val rectProperty = sportLightInfo.type as SpotlightType.Rect
    val context = LocalContext.current
    val density = LocalDensity.current
    val orientation = LocalConfiguration.current.orientation

    var overlayRootOffset by remember { mutableStateOf(Offset.Zero) }
    var displayCoordinates by remember { mutableStateOf(DisplayCoordinates()) }
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var showEndDot by remember { mutableStateOf(false) }
    var animationCompleted by remember { mutableStateOf(false) }
    val textMeasurer = rememberTextMeasurer()
    val scope = rememberCoroutineScope()

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
            Box(
                Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (animationCompleted)
                            onDismiss()
                    }
            )

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

                    val textPortraitPadding = 80.dp.toPx()
                    val textPadding = 125.dp.toPx()
                    val textLayoutResult = textMeasurer.measure(
                        text = sportLightInfo.text,
                        constraints = Constraints(
                            maxWidth = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                (size.width.toInt() / 2) - textPadding.toInt()
                            } else (size.width.toInt() - textPortraitPadding.toInt())
                        ),
                        style = TextStyle(
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

                    scope.launch {
                        delay(1.seconds)
                        animationCompleted = true
                    }
                }
            }

            // ── Pulsing ring + animated line ──────────────────────────────────
            Canvas(Modifier.fillMaxSize()) {

                // Pulsing rounded-rect border at the spotlight boundary
                drawRoundRect(
                    color = rectProperty.strokeColor.copy(alpha = scrimAlpha * rectBorderPulse()),
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