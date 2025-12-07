package com.droiddevtips.floatingtabbarandpip.common.videoPlayer.data

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

/**
 * The YouTube player configuration callback interface
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed interface YouTubePlayerConfigAction {

    data class OnPlayerReady(val youTubePlayer: YouTubePlayer): YouTubePlayerConfigAction
    data class OnStateChanged(val state: PlayerConstants.PlayerState): YouTubePlayerConfigAction
    data class OnVideoIDChanged(val videoID: String): YouTubePlayerConfigAction

}