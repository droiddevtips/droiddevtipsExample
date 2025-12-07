package com.droiddevtips.floatingtabbarandpip.common.videoPlayer

import android.app.Activity
import android.content.Context
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
fun YouTubePlayerView.configurePlayer(
    context: Context,
    onReady: (YouTubePlayer) -> Unit,
    onStateChange: (PlayerConstants.PlayerState) -> Unit
) {
    enableAutomaticInitialization = false
    val options =
        IFramePlayerOptions.Builder(context = context)
            .controls(1)
            .fullscreen(1)
            .build()

    initialize(
        youTubePlayerListener = CustomYouTubePlayerListener(
            onPlayerReady = onReady,
            onStateChange = onStateChange
        ),
        options
    )
}

fun YouTubePlayerView.addYouTubePlayerFullscreenListener(activity: Activity) {
    addFullscreenListener(CustomFullScreenListener(activity = activity))
}