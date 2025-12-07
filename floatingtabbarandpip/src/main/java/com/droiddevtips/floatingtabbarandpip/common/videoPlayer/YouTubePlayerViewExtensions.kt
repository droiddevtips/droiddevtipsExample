package com.droiddevtips.floatingtabbarandpip.common.videoPlayer

import android.app.Activity
import android.content.Context
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
fun YouTubePlayerView.configurePlayer(
    context: Context,
    action: (YouTubePlayerConfigAction) -> Unit
) {
    enableAutomaticInitialization = false
    val options =
        IFramePlayerOptions.Builder(context = context)
            .controls(1)
            .fullscreen(1)
            .build()

    initialize(
        youTubePlayerListener = CustomYouTubePlayerListener(
            action = action
        ),
        options
    )
}

fun YouTubePlayerView.addYouTubePlayerFullscreenListener(activity: Activity) {
    addFullscreenListener(CustomFullScreenListener(activity = activity))
}