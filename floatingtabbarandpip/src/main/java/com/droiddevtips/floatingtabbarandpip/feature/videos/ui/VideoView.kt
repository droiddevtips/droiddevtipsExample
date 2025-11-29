package com.droiddevtips.floatingtabbarandpip.feature.videos.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.floatingtabbarandpip.common.videoItem.VideoListDisplayItem
import com.droiddevtips.floatingtabbarandpip.feature.videos.data.VideosViewState

/**
 * The video composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun VideosView(
    viewState: VideosViewState,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    Box(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn(
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewState.videoList) {
                VideoListDisplayItem(item = it)
            }

            item {
                Spacer(modifier = Modifier.height(140.dp))
            }
        }
    }
}