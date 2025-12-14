package com.droiddevtips.floatingtabbarandpip.common.videoPlayer.domain

import android.util.Log
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.data.YouTubePlayerConfigAction
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

/**
 * The custom YouTube player listener, a subclass of [com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener]
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class CustomYouTubePlayerListener(
    private val action: (YouTubePlayerConfigAction) -> Unit,
) : AbstractYouTubePlayerListener() {

    override fun onReady(youTubePlayer: YouTubePlayer) {
        super.onReady(youTubePlayer)
        action(YouTubePlayerConfigAction.OnPlayerReady(youTubePlayer = youTubePlayer))
    }

    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
        super.onStateChange(youTubePlayer, state)
        action(YouTubePlayerConfigAction.OnStateChanged(state = state))
    }

    override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
        super.onError(youTubePlayer, error)
    }

    override fun onApiChange(youTubePlayer: YouTubePlayer) {
        super.onApiChange(youTubePlayer)
    }
    override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
        super.onVideoId(youTubePlayer, videoId)
        action(YouTubePlayerConfigAction.OnVideoIDChanged(videoID = videoId))
    }
}