package com.droiddevtips.floatingtabbarandpip.common.videoList.ui

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.UIEvent
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.VideoPlayerActivity
import com.droiddevtips.floatingtabbarandpip.core.ObserveEvents

/**
 * The main video list composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun MainVideoListView(
    videoListViewModel: VideoListViewModel,
    modifier: Modifier = Modifier
) {

    val videoListViewState =
        videoListViewModel.videoViewState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    VideoListView(
        viewState = videoListViewState.value,
        modifier = modifier,
        action = videoListViewModel::performAction
    )

    ObserveEvents(videoListViewModel.launchVideoPlayerEvent) { event ->
        when(event) {
            is UIEvent.LaunchVideoActivity -> {

                val videoPlayerActivityIntent = Intent(context, VideoPlayerActivity::class.java).also {
                    it.putExtra( VideoPlayerActivity.VIDEO_ID,event.videoID)
                    it.putExtra(VideoPlayerActivity.FAVORITE,event.favorite)
                    it.putExtra(VideoPlayerActivity.VIDEOS,event.videos)
                }
                context.startActivity(videoPlayerActivityIntent)
            }
        }
    }
}