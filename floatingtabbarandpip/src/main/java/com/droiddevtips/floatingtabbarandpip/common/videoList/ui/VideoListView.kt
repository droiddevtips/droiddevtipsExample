@file:OptIn(FlowPreview::class)

package com.droiddevtips.floatingtabbarandpip.common.videoList.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.DeviceOrientation
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoListAction
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoListViewState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

/**
 * The video list composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun VideoListView(
    viewState: VideoListViewState,
    modifier: Modifier = Modifier,
    onScrollPositionChanged: (VideoListAction) -> Unit
) {
    val windowSize = deviceDetectorCurrentWindowSize()

    Box(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {

        if (windowSize.device == Device.Tablet) {

            VideoListGridList(viewState = viewState, onScrollPositionChanged = onScrollPositionChanged)

        } else if (windowSize.orientation == DeviceOrientation.Landscape) {

            VideoListGridList(viewState = viewState, onScrollPositionChanged = onScrollPositionChanged)

        } else {

            val lazyListState =
                rememberLazyListState(initialFirstVisibleItemIndex = viewState.visibleIndex)
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

            LaunchedEffect(lazyListState) {
                snapshotFlow {
                    lazyListState.firstVisibleItemIndex
                }.debounce(500L)
                    .collectLatest { index ->
                        onScrollPositionChanged(VideoListAction.ScrollPosition(index = index))
                    }
            }
        }
    }
}

@Composable
private fun VideoListGridList(
    viewState: VideoListViewState,
    modifier: Modifier = Modifier,
    onScrollPositionChanged: (VideoListAction) -> Unit
) {
    val lazyGridState =
        rememberLazyGridState(initialFirstVisibleItemIndex = viewState.visibleIndex)

    LazyVerticalGrid(
        modifier = modifier,
        state = lazyGridState,
        contentPadding = PaddingValues(bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        columns = GridCells.Adaptive(minSize = 350.dp)
    ) {
        items(viewState.videoList) { item ->
            VideoListDisplayItem(item = item)
        }
    }

    LaunchedEffect(lazyGridState) {
        snapshotFlow {
            lazyGridState.firstVisibleItemIndex
        }.debounce(500L)
            .collectLatest { index ->
                onScrollPositionChanged(VideoListAction.ScrollPosition(index = index))
            }
    }
}
