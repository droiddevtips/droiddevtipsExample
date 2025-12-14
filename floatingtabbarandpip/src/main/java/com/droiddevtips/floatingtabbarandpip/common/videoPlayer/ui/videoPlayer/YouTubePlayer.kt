@file:Suppress("DEPRECATION")

package com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.videoPlayer

import android.app.Activity
import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Rational
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.droiddevtips.floatingtabbarandpip.common.pipManager.PipManager
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.data.VideoPlayerAction
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.data.VideoPlayerViewState
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.data.YouTubePlayerConfigAction
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.receiver.RemoteViewBroadcastReceiver
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.viewModel.VideoPlayerViewModel
import com.droiddevtips.floatingtabbarandpip.extensions.addYouTubePlayerFullscreenListener
import com.droiddevtips.floatingtabbarandpip.extensions.configurePlayer
import com.droiddevtips.floatingtabbarandpip.util.AppDrawable
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/**
 * The YouTube player view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun YouTubePlayerContainerView(
    activity: Activity,
    videoState: State<VideoPlayerViewState>,
    viewModel: VideoPlayerViewModel,
    youTubePlayerView: (YouTubePlayerView) -> Unit,
    youTubePlayer: (YouTubePlayer) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.background(color = Color.Red)) {

        YouTubePlayer(
            activity = activity,
            videoState = videoState,
            viewModel = viewModel,
            modifier = modifier,
            youTubePlayer = youTubePlayer,
            youTubePlayerView = youTubePlayerView
        )

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

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        activity.enterPictureInPictureMode(createRemoteActionPendingIntent(activity = activity))
                    } else {
                        activity.enterPictureInPictureMode()
                    }
                    PipManager.updatePipModeState(pipMode = true)
                    viewModel.handleAction(
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
}

@RequiresApi(Build.VERSION_CODES.O)
private fun createRemoteActionPendingIntent(activity: Activity): PictureInPictureParams {

    val pipParams =
        PictureInPictureParams.Builder().setAspectRatio(
            Rational(16, 9)
        )

    val mainActivity = Intent(
        activity,
        RemoteViewBroadcastReceiver::class.java
    ).apply {
        action =
            RemoteViewBroadcastReceiver.CUSTOM_ACTION_BUTTON
    }
    val pendingIntent = PendingIntent.getBroadcast(
        activity,
        101,
        mainActivity,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val icon = Icon.createWithResource(
        activity,
        AppDrawable.favorite_icon
    )
    val customAction = RemoteAction(
        icon,
        "Custom",
        "Custom action",
        pendingIntent
    )
    pipParams.apply {
        setAutoEnterEnabled(true)
        setSeamlessResizeEnabled(true)
        setActions(listOf(customAction))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            setCloseAction(customAction) // Combine this with a action button from the list and a red circle appear around the action button indicating it is close button.
            setTitle("Tit") // is not working at the time of writing this article
            setSubtitle("Sub") // is not working at the time of writing this article
        }
    }

    return pipParams.build()
}


@Composable
private fun YouTubePlayer(
    activity: Activity,
    videoState: State<VideoPlayerViewState>,
    viewModel: VideoPlayerViewModel,
    youTubePlayerView: (YouTubePlayerView) -> Unit,
    youTubePlayer: (YouTubePlayer) -> Unit,
    modifier: Modifier = Modifier
) {

    AndroidView(
        modifier = modifier,
        factory = { context ->
            YouTubePlayerView(context = context).apply {

                youTubePlayerView(this)
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                configurePlayer(
                    context = context,
                    action = { action ->

                        when (action) {
                            is YouTubePlayerConfigAction.OnPlayerReady -> {
                                youTubePlayer(action.youTubePlayer)
                                if (videoState.value.videoID.isNotBlank()) {
                                    action.youTubePlayer.loadVideo(
                                        videoState.value.videoID,
                                        0f
                                    )
                                }
                            }

                            is YouTubePlayerConfigAction.OnStateChanged -> {
                                viewModel.apply {
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
                                viewModel.apply {
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

                addYouTubePlayerFullscreenListener(activity = activity)
            }
        }
    )
}