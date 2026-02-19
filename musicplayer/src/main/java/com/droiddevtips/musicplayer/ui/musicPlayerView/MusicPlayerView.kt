@file:OptIn(ExperimentalMaterial3Api::class)

package com.droiddevtips.musicplayer.ui.musicPlayerView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.musicplayer.R
import com.droiddevtips.musicplayer.ui.mainView.data.MusicPlayerViewState
import com.droiddevtips.musicplayer.ui.mainView.data.MusicPlayerAction

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun MusicPlayerView(
    viewState: MusicPlayerViewState,
    modifier: Modifier = Modifier,
    action: (MusicPlayerAction) -> Unit
) {

    val musicTrack = viewState.currentlyPlaying ?: return

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                Image(
                    painter = painterResource(id = musicTrack.thumbnailLarge),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(24.dp)),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "${musicTrack.song} - ${musicTrack.composer}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee(),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )

                Text(
                    musicTrack.credit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee(),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
            }

            Column {
                LinearProgressIndicator(
                    progress = { viewState.percentage.toFloat() },
                    modifier = Modifier.fillMaxWidth(),
                    gapSize = 0.dp,
                    strokeCap = StrokeCap.Butt,
                    drawStopIndicator = {}
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        viewState.positionDisplayText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        viewState.totalDurationDisplayText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                IconButton(enabled = viewState.enablePreviousButton, onClick = {
                    action(MusicPlayerAction.Previous)
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.prev_button),
                        modifier = Modifier
                            .size(35.dp)
                            .alpha(if (viewState.enablePreviousButton) 1f else 0.38f),
                        colorFilter = ColorFilter.tint(color = if (isSystemInDarkTheme()) Color.White else Color.Black),
                        contentDescription = null
                    )
                }

                if (viewState.showPauseButton) {
                    IconButton(onClick = {
                        action(MusicPlayerAction.Pause)
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.pause_button),
                            modifier = Modifier.size(55.dp),
                            colorFilter = ColorFilter.tint(color = if (isSystemInDarkTheme()) Color.White else Color.Black),
                            contentDescription = null
                        )
                    }
                }

                if (viewState.showPlayButton) {
                    IconButton(onClick = {
                        action(MusicPlayerAction.Play)
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.play_button),
                            modifier = Modifier.size(55.dp),
                            colorFilter = ColorFilter.tint(color = if (isSystemInDarkTheme()) Color.White else Color.Black),
                            contentDescription = null
                        )
                    }
                }


                IconButton(enabled = viewState.enableNextButton, onClick = {
                    action(MusicPlayerAction.Next)
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.next_button),
                        modifier = Modifier
                            .size(35.dp)
                            .alpha(if (viewState.enableNextButton) 1f else 0.38f),
                        colorFilter = ColorFilter.tint(color = if (isSystemInDarkTheme()) Color.White else Color.Black),
                        contentDescription = null
                    )
                }
            }
        }
    }
}