package com.droiddevtips.floatingtabbarandpip.common.videoPlayer.data

import android.content.Intent
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants

/**
 * The video player action interface
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed interface VideoPlayerAction {

    data class PlayerStateUpdate(val state: PlayerConstants.PlayerState): VideoPlayerAction
    data class HandleIntent(val intent: Intent): VideoPlayerAction
    data class TogglePipModeState(val isInPipMode: Boolean): VideoPlayerAction
    data class VideoIDUpdate(val videoID: String): VideoPlayerAction

}