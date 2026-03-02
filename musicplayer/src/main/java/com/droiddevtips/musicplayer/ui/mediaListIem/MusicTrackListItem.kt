package com.droiddevtips.musicplayer.ui.mediaListIem

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.musicplayer.ui.mainView.data.MusicListItemPreviewProvider
import com.droiddevtips.musicplayer.ui.mainView.data.MusicTrack
import com.droiddevtips.musicplayer.ui.audioVisualizer.AudioVisualizer
import com.droiddevtips.typography.extensions.clickableWithPrimaryColorRipple

/**
 * The music track list item composable
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
    showAudioVisualizer: Boolean = false,
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

            Row(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
            ) {
                if (currentlyPlaying == musicTrack.id && showAudioVisualizer) {
                    AudioVisualizer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.Bottom)
                            .height(20.dp)
                    )
                }
            }
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

