package com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoItem
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoListAction
import com.droiddevtips.floatingtabbarandpip.common.videoList.ui.VideoListDisplayItem

/**
 * The video grid list view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun VideoGridList(
    lazyGridState: LazyGridState,
    nowPlayingID: String,
    videoItems: List<VideoItem>,
    modifier: Modifier = Modifier,
    onLoadNewVideoID: (String) -> Unit
) {

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Adaptive(minSize = 230.dp),
        modifier = modifier
            .background(color = Color.Black),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(videoItems) { videoItem ->
            VideoListDisplayItem(
                isPlaying = nowPlayingID == videoItem.id,
                item = videoItem,
                action = {

                    when (it) {
                        is VideoListAction.LaunchYouTubePlayer -> {
                            onLoadNewVideoID(it.videoID)
                        }

                        else -> Unit
                    }
                }
            )
        }
    }
}