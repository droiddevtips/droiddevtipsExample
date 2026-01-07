@file:Suppress("DEPRECATION")

package com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.videoPlayer

import android.app.Activity
import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Rational
import android.view.ViewGroup
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
                if (activity.packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
                    IconButton(onClick = {

                        activity.enterPictureInPictureMode(
                            createRemoteActionPendingIntent(
                                activity = activity
                            )
                        )
                        PipManager.updatePipModeState(pipMode = true)
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
}

private fun createRemoteActionPendingIntent(activity: Activity): PictureInPictureParams {

    val pipParams =
        PictureInPictureParams.Builder().setAspectRatio(
            Rational(16, 9)
        )

    val playRemoteAction = createRemoteAction(activity = activity, icon = AppDrawable.play, title = "Play", contentDescription = "PIP window play button", remoteIntentAction = RemoteViewBroadcastReceiver.ACTION_PLAY_BUTTON)
    val pauseRemoteAction = createRemoteAction(activity = activity, icon = AppDrawable.pause, title = "Pause", contentDescription = "PIP window pause button", remoteIntentAction = RemoteViewBroadcastReceiver.ACTION_PAUSE_BUTTON)
    val closeRemoteAction = createRemoteAction(activity = activity, icon = AppDrawable.close_icon, title = "Close", contentDescription = "PIP window close button", remoteIntentAction = RemoteViewBroadcastReceiver.ACTION_CLOSE_BUTTON)

    pipParams.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setAutoEnterEnabled(true)
            setSeamlessResizeEnabled(false)
        }
        setAspectRatio(Rational(16, 9))
        setActions(listOf(playRemoteAction, pauseRemoteAction, closeRemoteAction))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            setCloseAction(closeRemoteAction) // Combine this with a action button from the list and a red circle appear around the action button indicating it is close button.
            setTitle("Tit") // is not working at the time of writing this article
            setSubtitle("Sub") // is not working at the time of writing this article
        }
    }

    return pipParams.build()
}

private fun createRemoteAction(
    activity: Activity,
    icon: Int,
    title: String,
    contentDescription: String,
    remoteIntentAction: String
): RemoteAction {

    val intent = createRemoteActionPendingIntent(activity = activity, remoteAction = remoteIntentAction)

    val pendingIntent = PendingIntent.getBroadcast(
        activity,
        101,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )
    val icon = Icon.createWithResource(
        activity,
        icon
    )
    return RemoteAction(
        icon,
        title,
        contentDescription,
        pendingIntent
    )
}

private fun createRemoteActionPendingIntent(
    activity: Activity,
    remoteAction: String
): Intent {
    return Intent(
        activity,
        RemoteViewBroadcastReceiver::class.java
    ).apply {
        action = remoteAction
    }
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