@file:Suppress("DEPRECATION", "COMPOSE_APPLIER_CALL_MISMATCH")

package com.droiddevtips.floatingtabbarandpip.common.videoPlayer

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.droiddevtips.floatingtabbarandpip.util.AppDrawable
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
    private val viewModel: VideoPlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel.handleAction(action = VideoPlayerAction.HandleIntent(intent = intent))
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {

                val videoState = viewModel.videoPlayerViewState.collectAsStateWithLifecycle()
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
                                            onReady = { youTubePlayer ->
                                                this@VideoPlayerActivity.youTubePlayer =
                                                    youTubePlayer
                                                if (videoState.value.videoID.isNotBlank()) {
                                                    youTubePlayer.loadVideo(
                                                        videoState.value.videoID,
                                                        0f
                                                    )
                                                }
                                            },
                                            onStateChange = { state ->
                                                this@VideoPlayerActivity.viewModel.apply {
                                                    handleAction(
                                                        action = VideoPlayerAction.PlayerStateUpdate(
                                                            state = state
                                                        )
                                                    )
                                                    handleAction(
                                                        action = VideoPlayerAction.TogglePipButtonVisibility(
                                                            visibility = state == PlayerConstants.PlayerState.PLAYING
                                                        )
                                                    )
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

                        Text("Video player activity")
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
                    Log.i("TAG23", "Video ID updated: ${videoState.value.videoID}")
                    Log.i("TAG23", "Video player state: ${videoState.value.playerState}")
                    Log.i("TAG23", "Youtube player: $youTubePlayer")
                    if (videoState.value.videoID.isNotBlank() && videoState.value.playerState == PlayerConstants.PlayerState.PLAYING) {
                        youTubePlayer?.loadVideo(videoState.value.videoID, 0f)
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