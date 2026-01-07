@file:Suppress("COMPOSE_APPLIER_CALL_MISMATCH")

package com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui

import android.app.ComponentCaller
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.DeviceOrientation
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.floatingtabbarandpip.common.pipManager.PipManager
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.videoRepository.VideoRepositoryImpl
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.data.UIEvent
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.data.VideoPlayerAction
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.metaData.MetaDataView
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.pipState.rememberPipUiState
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.receiver.RemoteViewBroadcastReceiver
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.videoList.VideoGridList
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.videoList.VideoLazyList
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.videoPlayer.YouTubePlayerContainerView
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.viewModel.VideoPlayerViewModel
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.viewModel.VideoPlayerViewModelFactory
import com.droiddevtips.floatingtabbarandpip.core.ObserveEvents
import com.droiddevtips.typography.DroidDevTipsTheme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/**
 * The video player activity
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class VideoPlayerActivity : ComponentActivity() {

    companion object {
        const val VIDEO_ID = "video_id"
        const val FAVORITE = "favorite"
        const val VIDEOS = "videos"
    }

    private var youTubePlayerView: YouTubePlayerView? = null
    private var youTubePlayer: YouTubePlayer? = null
    private val viewModel: VideoPlayerViewModel by viewModels(factoryProducer = {
        VideoPlayerViewModelFactory(
            VideoRepositoryImpl
        )
    })

    private val remoteViewActionReceiver = RemoteViewBroadcastReceiver()
    private val filter = IntentFilter().apply {
        addAction(RemoteViewBroadcastReceiver.ACTION_CLOSE_BUTTON)
        addAction(RemoteViewBroadcastReceiver.ACTION_PLAY_BUTTON)
        addAction(RemoteViewBroadcastReceiver.ACTION_PAUSE_BUTTON)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel.handleAction(action = VideoPlayerAction.HandleIntent(intent = intent))
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {

                val windowSize = deviceDetectorCurrentWindowSize()
                val videoState = viewModel.videoPlayerViewState.collectAsStateWithLifecycle()
                val listState = rememberLazyListState()
                val gridState = rememberLazyGridState()
                val pipUiState by rememberPipUiState()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                ) { padding ->

                    Column(
                        modifier = Modifier
                            .padding(paddingValues = padding)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {

                            Column(
                                modifier = Modifier
                                    .then(

                                        if (videoState.value.isInPip) {
                                            Modifier.fillMaxWidth()
                                        } else {
                                            if (windowSize.device == Device.Tablet) {

                                                if (windowSize.orientation == DeviceOrientation.Landscape) {
                                                    Modifier
                                                        .fillMaxWidth(fraction = 0.65f)
                                                } else {
                                                    Modifier
                                                        .fillMaxWidth()
                                                }
                                            } else {
                                                if (windowSize.orientation == DeviceOrientation.Landscape) {
                                                    Modifier
                                                        .fillMaxWidth(fraction = 0.65f)

                                                } else {
                                                    Modifier
                                                        .fillMaxWidth()
                                                }
                                            }
                                        }
                                    )
                            ) {

                                YouTubePlayerContainerView(
                                    activity = this@VideoPlayerActivity,
                                    videoState = videoState,
                                    viewModel = viewModel,
                                    modifier = Modifier
                                        .then(

                                            if (videoState.value.isInPip) {
                                                Modifier.fillMaxWidth()
                                            } else {
                                                if (windowSize.device == Device.Tablet) {
                                                    Modifier.fillMaxWidth()
                                                } else {
                                                    if (windowSize.orientation == DeviceOrientation.Landscape) {
                                                        Modifier
                                                            .fillMaxWidth()
                                                            .fillMaxHeight(fraction = 0.76f)
                                                    } else {
                                                        Modifier
                                                            .fillMaxWidth()
                                                    }
                                                }
                                            }
                                        ),
                                    youTubePlayerView = { playerView ->
                                        this@VideoPlayerActivity.youTubePlayerView = playerView
                                    },
                                    youTubePlayer = { youTubePlayer ->
                                        this@VideoPlayerActivity.youTubePlayer = youTubePlayer
                                    }
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = MaterialTheme.colorScheme.background)
                                ) {

                                    MetaDataView(metaData = videoState.value.videoDetail)

                                    if (windowSize.orientation == DeviceOrientation.Portrait) {

                                        if (windowSize.device == Device.Tablet) {

                                            VideoGridList(
                                                lazyGridState = gridState,
                                                nowPlayingID = videoState.value.videoID,
                                                videoItems = videoState.value.items,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight(),
                                                onLoadNewVideoID = { videoID ->
                                                    youTubePlayer?.loadVideo(
                                                        videoId = videoID,
                                                        0f
                                                    )
                                                }
                                            )
                                        } else {

                                            VideoLazyList(
                                                listState = listState,
                                                nowPlayingVideoID = videoState.value.videoID,
                                                videoItems = videoState.value.items,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight(),
                                                onNewVideoLoaded = { videoID ->
                                                    youTubePlayer?.loadVideo(
                                                        videoId = videoID,
                                                        0f
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            if (windowSize.orientation == DeviceOrientation.Landscape && !videoState.value.isInPip) {

                                VerticalDivider(thickness = 1.dp, color = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray)

                                VideoLazyList(
                                    listState = listState,
                                    nowPlayingVideoID = videoState.value.videoID,
                                    videoItems = videoState.value.items,
                                    verticalArrangement = Arrangement.spacedBy(if (windowSize.device == Device.Tablet) 8.dp else 0.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(),
                                    onNewVideoLoaded = { videoID ->
                                        youTubePlayer?.loadVideo(
                                            videoId = videoID,
                                            0f
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

                LaunchedEffect(pipUiState) {
                    pipUiState?.let { state ->
                        this@VideoPlayerActivity.viewModel.apply {
                            handleAction(action = VideoPlayerAction.TogglePipModeState(isInPipMode = state.isInPictureInPictureMode))
                        }
                    }
                }

                LaunchedEffect(videoState.value.videoID) {
                    if (videoState.value.videoID.isNotBlank() && videoState.value.playerState == PlayerConstants.PlayerState.PLAYING) {
                        youTubePlayer?.loadVideo(videoState.value.videoID, 0f)
                    }
                }

                ObserveEvents(viewModel.uiEvents) { event ->
                    when (event) {
                        is UIEvent.ScrollToIndex -> {
                            listState.animateScrollToItem(event.index)
                            gridState.animateScrollToItem(event.index)
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent, caller: ComponentCaller) {
        super.onNewIntent(intent, caller)
        this.viewModel.handleAction(action = VideoPlayerAction.HandleIntent(intent = intent))
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            ContextCompat.registerReceiver(
                this,
                remoteViewActionReceiver,
                filter,
                RECEIVER_NOT_EXPORTED
            )
        } else {
            registerReceiver(remoteViewActionReceiver, filter, RECEIVER_NOT_EXPORTED)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(remoteViewActionReceiver)
        if (PipManager.isInPipMode) {
            finish()
        }
    }

    private fun releasePlayer() {
        this.youTubePlayerView?.release()
        this.youTubePlayerView = null
        PipManager.updatePipModeState(pipMode = false)
    }
}