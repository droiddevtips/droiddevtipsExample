package com.droiddevtips.musicplayer.mainView

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.musicplayer.R
import com.droiddevtips.typography.extensions.clickableWithPrimaryColorRipple
import com.droiddevtips.typography.extensions.dpToPx

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */

enum class PlayerUiState {
    MINI,
    EXPANDED
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun MiniPlayerContainer(
    viewState: MusicPlayerViewState,
    modifier: Modifier = Modifier,
    onViewAction: (MusicPlayerAction) -> Unit
) {

    val miniPlayerViewState = rememberSaveable {
        mutableStateOf(PlayerUiState.MINI)
    }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val height by animateDpAsState(
        targetValue = if (miniPlayerViewState.value == PlayerUiState.EXPANDED)
            screenHeight
        else
            100.dp
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .pointerInput(Unit) {
                detectVerticalDragGestures { _, dragAmount ->
                    if (dragAmount > 10) {
                        miniPlayerViewState.value = PlayerUiState.MINI
                    }

                    if (dragAmount < -10) {
                        miniPlayerViewState.value = PlayerUiState.EXPANDED
                    }
                }
            },
        tonalElevation = 8.dp,
        color = Color.Transparent
    ) {
        if (miniPlayerViewState.value == PlayerUiState.MINI) {
            MiniPlayer(
                viewState = viewState,
                onExpanded = {
                    miniPlayerViewState.value = PlayerUiState.EXPANDED
                },
                onViewAction = onViewAction
            )
        } else {
            MiniPlayerExpandedView(
                viewState = viewState,
                onCollapsed = {
                    miniPlayerViewState.value = PlayerUiState.MINI
                },
                onViewAction = { }
            )
        }
    }
}

@Composable
fun MiniPlayer(
    viewState: MusicPlayerViewState,
    modifier: Modifier = Modifier,
    onExpanded: () -> Unit,
    onViewAction: (MusicPlayerAction) -> Unit
) {

    val currentlyPlayer = viewState.currentlyPlaying ?: return

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = if (isSystemInDarkTheme()) Color.Black else Color(0xFFE7E7E7),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .padding(all = 8.dp),
        ) {

            Row(
                modifier = Modifier.align(Alignment.Center),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .clip(shape = RoundedCornerShape(size = 8.dp))
                        .clickableWithPrimaryColorRipple {
                            onExpanded()
                        }
                ) {

                    Image(
                        painter = painterResource(id = currentlyPlayer.thumbnailLarge),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(shape = RoundedCornerShape(size = 8.dp))
                            .align(alignment = Alignment.Center)
                    )

                    LinearProgressIndicator(
                        progress = { viewState.percentage.toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.BottomCenter),
                        color = MaterialTheme.colorScheme.primary,
                        gapSize = 0.dp,
                        strokeCap = StrokeCap.Butt,
                        drawStopIndicator = {}
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                    Text(
                        "${currentlyPlayer.song} - ${currentlyPlayer.composer}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 25.dp)
                            .basicMarquee(),
                        textAlign = TextAlign.Center
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        IconButton(
                            modifier = Modifier.size(25.dp),
                            enabled = viewState.enablePreviousButton,
                            onClick = {
                                onViewAction(MusicPlayerAction.Previous)
                            }) {
                            Icon(
                                painter = painterResource(id = R.drawable.prev_button),
                                modifier = Modifier.size(20.dp),
                                contentDescription = null
                            )
                        }

                        IconButton(modifier = Modifier.size(25.dp), onClick = {
                            if (viewState.showPauseButton) {
                                onViewAction(MusicPlayerAction.Pause)
                            } else {
                                onViewAction(MusicPlayerAction.Play)
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = if (viewState.showPauseButton) R.drawable.pause_button else R.drawable.play_button),
                                modifier = Modifier.size(25.dp),
                                contentDescription = null
                            )
                        }

                        IconButton(
                            enabled = viewState.enableNextButton,
                            modifier = Modifier.size(25.dp),
                            onClick = {
                                onViewAction(MusicPlayerAction.Next)
                            }) {
                            Icon(
                                painter = painterResource(id = R.drawable.next_button),
                                modifier = Modifier.size(20.dp),
                                contentDescription = null
                            )
                        }
                    }
                }
            }

            IconButton(
                modifier = Modifier
                    .size(15.dp)
                    .align(alignment = Alignment.TopEnd),
                onClick = {
                    onViewAction(MusicPlayerAction.CloseMiniPlayer)
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.close),
                    modifier = Modifier.size(15.dp),
                    contentDescription = null
                )
            }
        }
    }
}


@Composable
fun MiniPlayerExpandedView(
    viewState: MusicPlayerViewState,
    modifier: Modifier = Modifier,
    onCollapsed: () -> Unit,
    onViewAction: (MusicPlayerAction) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color.Black)
        )

        Text("Video title", style = MaterialTheme.typography.titleLarge)

        Button(onClick = {
            onViewAction(MusicPlayerAction.ToggleMiniPlayerView(expanded = false))
        }) {
            Text("Collapse")
        }
    }
}