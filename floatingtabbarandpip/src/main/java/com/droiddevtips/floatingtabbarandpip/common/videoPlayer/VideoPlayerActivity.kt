@file:Suppress("DEPRECATION", "COMPOSE_APPLIER_CALL_MISMATCH")

package com.droiddevtips.floatingtabbarandpip.common.videoPlayer

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoListAction
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.videoRepository.VideoRepositoryImpl
import com.droiddevtips.floatingtabbarandpip.common.videoList.ui.VideoListDisplayItem
import com.droiddevtips.floatingtabbarandpip.core.ObserveEvents
import com.droiddevtips.floatingtabbarandpip.util.AppDrawable
import com.droiddevtips.floatingtabbarandpip.util.AppString
import com.droiddevtips.typography.DroidDevTipsTheme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel.handleAction(action = VideoPlayerAction.HandleIntent(intent = intent))
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {

                val videoState = viewModel.videoPlayerViewState.collectAsStateWithLifecycle()
                val listState = rememberLazyListState()
                val pipUiState by rememberPipUiState()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                        .navigationBarsPadding()
                ) { padding ->
                    Column(modifier = Modifier.padding(paddingValues = padding)) {

                        Box {
                            AndroidView(
                                modifier = Modifier.fillMaxWidth(),
                                factory = { context ->
                                    YouTubePlayerView(context = context).apply {

                                        this@VideoPlayerActivity.youTubePlayerView = this

                                        configurePlayer(
                                            context = context,
                                            action = { action ->

                                                when (action) {
                                                    is YouTubePlayerConfigAction.OnPlayerReady -> {
                                                        this@VideoPlayerActivity.youTubePlayer =
                                                            action.youTubePlayer
                                                        if (videoState.value.videoID.isNotBlank()) {
                                                            action.youTubePlayer.loadVideo(
                                                                videoState.value.videoID,
                                                                0f
                                                            )
                                                        }
                                                    }

                                                    is YouTubePlayerConfigAction.OnStateChanged -> {
                                                        this@VideoPlayerActivity.viewModel.apply {
                                                            handleAction(
                                                                action = VideoPlayerAction.PlayerStateUpdate(
                                                                    state = action.state
                                                                )
                                                            )
                                                            handleAction(
                                                                action = VideoPlayerAction.TogglePipButtonVisibility(
                                                                    visibility = action.state == PlayerConstants.PlayerState.PLAYING
                                                                )
                                                            )
                                                        }
                                                    }

                                                    is YouTubePlayerConfigAction.OnVideoIDChanged -> {
                                                        this@VideoPlayerActivity.viewModel.apply {
                                                            handleAction(
                                                                action = VideoPlayerAction.VideoIDUpdate(
                                                                    videoID = action.videoID
                                                                )
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        )

                                        addYouTubePlayerFullscreenListener(activity = this@VideoPlayerActivity)
                                    }
                                })

                            Column(
                                modifier = Modifier.align(Alignment.TopStart)
                            ) {
                                AnimatedVisibility(
                                    visible = videoState.value.showPipButton,
                                    enter = fadeIn(),
                                    exit = fadeOut(
                                        tween(500)
                                    )
                                ) {
                                    IconButton(onClick = {
                                        enterPictureInPictureMode()
                                        this@VideoPlayerActivity.viewModel.handleAction(
                                            action = VideoPlayerAction.TogglePipButtonVisibility(
                                                visibility = false
                                            )
                                        )
                                    }) {
                                        Image(
                                            painter = painterResource(id = AppDrawable.pip_icon),
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(horizontal = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(modifier = Modifier.padding(top = 6.dp)) {
                                    Image(
                                        painter = painterResource(id = AppDrawable.playlist_icon),
                                        modifier = Modifier.align(alignment = Alignment.Center).size(24.dp),
                                        contentDescription = null
                                    )
                                }


                                Text(
                                    text = if (videoState.value.videos) stringResource(id = AppString.other_videos) else stringResource(
                                        id = AppString.favorite_videos
                                    ),
                                    fontSize = 16.sp,
                                    lineHeight = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }

                            LazyColumn(
                                state = listState,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(videoState.value.items) { videoItem ->
                                    VideoListDisplayItem(
                                        isPlaying = videoState.value.videoID == videoItem.id,
                                        item = videoItem,
                                        action = {

                                            when (it) {
                                                is VideoListAction.LaunchYouTubePlayer -> {
                                                    youTubePlayer?.loadVideo(
                                                        videoId = it.videoID,
                                                        0f
                                                    )
                                                }

                                                else -> Unit
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                LaunchedEffect(pipUiState) {
                    pipUiState?.let { state ->
                        this@VideoPlayerActivity.viewModel.handleAction(
                            action = VideoPlayerAction.TogglePipButtonVisibility(
                                visibility = !state.isInPictureInPictureMode
                            )
                        )
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

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        if (isInPictureInPictureMode)
            releasePlayer()
    }

    private fun releasePlayer() {
        this.youTubePlayerView?.release()
        this.youTubePlayerView = null
    }
}