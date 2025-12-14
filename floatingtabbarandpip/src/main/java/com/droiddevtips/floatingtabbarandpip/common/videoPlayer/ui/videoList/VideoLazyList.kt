package com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.videoList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoItem
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoListAction
import com.droiddevtips.floatingtabbarandpip.common.videoList.ui.VideoListDisplayItem

/**
 * The video lazy list view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun VideoLazyList(
    listState: LazyListState,
    nowPlayingVideoID: String,
    videoItems: List<VideoItem>,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    modifier: Modifier = Modifier,
    onNewVideoLoaded: (String) -> Unit
) {

    LazyColumn(
        state = listState,
        modifier = modifier
            .background(color = Color.Black),
        verticalArrangement = verticalArrangement
    ) {

        items(videoItems) { videoItem ->
            VideoListDisplayItem(
                isPlaying = nowPlayingVideoID == videoItem.id,
                item = videoItem,
                action = {

                    when (it) {
                        is VideoListAction.LaunchYouTubePlayer -> {
                            onNewVideoLoaded(it.videoID)
                        }

                        else -> Unit
                    }
                }
            )
        }
    }
}