package com.droiddevtips.floatingtabbarandpip.common.videoPlayer

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.VideoPlayerActivity.Companion.FAVORITE
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.VideoPlayerActivity.Companion.VIDEOS
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.VideoPlayerActivity.Companion.VIDEO_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class VideoPlayerViewModel : ViewModel() {

    private var _videoPlayerViewState = MutableStateFlow(VideoPlayerViewState())
    val videoPlayerViewState: StateFlow<VideoPlayerViewState>
        get() = _videoPlayerViewState.asStateFlow()

    fun handleAction(action: VideoPlayerAction) {
        when(action) {
            is VideoPlayerAction.HandleIntent -> handleIntent(intent = action.intent)
            is VideoPlayerAction.PlayerStateUpdate -> {
                //TODO: When player state ENDED go to next video item of the list
                _videoPlayerViewState.update { it.copy(playerState = action.state) }
            }
            is VideoPlayerAction.TogglePipButtonVisibility -> {
                _videoPlayerViewState.update { it.copy(showPipButton = action.visibility) }
            }
        }
    }

    private fun handleIntent(intent: Intent?) {

        if (intent == null)
            return

        intent.extras?.let { intentBundle ->

            val videoID = intentBundle.getString(VIDEO_ID) ?: ""
            val favorite = intentBundle.getBoolean(FAVORITE)
            val videos = intentBundle.getBoolean(VIDEOS)

            if (_videoPlayerViewState.value.videoID.isBlank()) {
                _videoPlayerViewState.update { it.copy(videoID = videoID, favorite = favorite, videos = videos) }
            } else {

                if (videoID.isNotBlank() && videoID != _videoPlayerViewState.value.videoID) {
                    _videoPlayerViewState.update { it.copy(videoID = videoID) }
                }

                if (favorite != _videoPlayerViewState.value.favorite) {
                    _videoPlayerViewState.update { it.copy(favorite = favorite) }
                }

                if (videos != _videoPlayerViewState.value.videos) {
                    _videoPlayerViewState.update { it.copy(videos = videos) }
                }
            }
        }
    }
}