package com.droiddevtips.musicplayer.mainView

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.typography.extensions.clickableWithPrimaryColorRipple

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Preview(
    name = "Light Mode",
    backgroundColor = 0xFFFFFFFF,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode",
    backgroundColor = 0xFF2A2A2A,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MusicTrackListItem(
    @PreviewParameter(MusicListItemPreviewProvider::class) musicTrack: MusicTrack,
    currentlyPlaying: String = "",
    textColor: Color = if (isSystemInDarkTheme()) Color.White else Color.Black,
    modifier: Modifier = Modifier,
    onMusicTrackSelected: (MusicTrack) -> Unit = {}
) {
    Column(
        modifier = modifier.clickableWithPrimaryColorRipple {
            onMusicTrackSelected(musicTrack)
        },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Row(
            modifier = modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Image(
                    modifier = Modifier.size(80.dp),
                    painter = painterResource(id = musicTrack.thumbnail),
                    contentScale = ContentScale.Crop,
                    contentDescription = musicTrack.song
                )

                Column {
                    Text(text = musicTrack.song, fontWeight = FontWeight.Bold, color = textColor)
                    Text(text = musicTrack.composer, color = textColor)
                    Text(text = musicTrack.credit, color = textColor, fontSize = 10.sp)
                }
            }

            Row(modifier = Modifier
                .width(50.dp)
                .height(50.dp)
            ) {
                if (currentlyPlaying == musicTrack.id) {
                    AudioVisualizer(
                        modifier = Modifier
                            .fillMaxWidth().align(alignment = Alignment.Bottom)
                            .height(20.dp)
                    )
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
    }
}

@Composable
fun AudioVisualizer(
    modifier: Modifier = Modifier,
    barCount: Int = 3,
    barColor: Color = MaterialTheme.colorScheme.primary
) {
    val infiniteTransition = rememberInfiniteTransition(label = "visualizer")

    val randomHeights = List(barCount) { index ->
        infiniteTransition.animateFloat(
            initialValue = 0.2f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = (400..900).random(),
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bar_$index"
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        randomHeights.forEach { animatedValue ->
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight(animatedValue.value)
                    .clip(RoundedCornerShape(50))
                    .background(barColor)
            )
        }
    }
}

