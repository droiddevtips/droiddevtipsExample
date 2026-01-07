package com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.viewModel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoDetails
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.videoRepository.VideoRepository
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.data.UIEvent
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.data.VideoPlayerAction
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.data.VideoPlayerViewState
import com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.VideoPlayerActivity
import com.droiddevtips.floatingtabbarandpip.core.videosRepository
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The video player view model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class VideoPlayerViewModel(private val repository: VideoRepository) : ViewModel() {

    private var _videoPlayerViewState = MutableStateFlow(VideoPlayerViewState())
    val videoPlayerViewState: StateFlow<VideoPlayerViewState>
        get() = _videoPlayerViewState.asStateFlow()

    private val _uiEvent = Channel<UIEvent>()
    val uiEvents = _uiEvent.receiveAsFlow()

    fun handleAction(action: VideoPlayerAction) {
        when (action) {
            is VideoPlayerAction.HandleIntent -> handleIntent(intent = action.intent)
            is VideoPlayerAction.PlayerStateUpdate -> {
                if (action.state == PlayerConstants.PlayerState.ENDED) {

                    val items = _videoPlayerViewState.value.items

                    if (_videoPlayerViewState.value.nowPlayingVideoIndex >= items.lastIndex) {

                        val firstVideoID = if (items.isNotEmpty()) items[0].id else ""
                        _videoPlayerViewState.update { it.copy(videoID = firstVideoID) }
                    } else {
                        val nextVideoID = items[_videoPlayerViewState.value.nowPlayingVideoIndex + 1].id
                        _videoPlayerViewState.update { it.copy(videoID = nextVideoID) }
                    }
                } else {
                    _videoPlayerViewState.update { it.copy(playerState = action.state) }
                }
            }

            is VideoPlayerAction.VideoIDUpdate -> {
                val newVideoItem = _videoPlayerViewState.value.items.asSequence().filter { it.id == action.videoID }.firstOrNull()
                val newVideoIndex = newVideoItem?.let {
                   _videoPlayerViewState.value.items.indexOf(it)
                }?:run {
                  0
                }
                _videoPlayerViewState.update { it.copy(videoID = action.videoID, nowPlayingVideoIndex = newVideoIndex) }
                loadVideoDetail(videoID = action.videoID)
            }

            is VideoPlayerAction.TogglePipModeState -> {
                _videoPlayerViewState.update { it.copy(isInPip = action.isInPipMode, showPipButton = !action.isInPipMode) }
            }
        }
    }

    fun loadVideoDetail(videoID:String) {

        if (videoID.isBlank())
            return

        if (!_videoPlayerViewState.value.isLoadingDetail) {
            _videoPlayerViewState.update { it.copy(isLoadingDetail = true) }
        }

        viewModelScope.launch {
            val videoDetail = videosRepository.loadVideoDetail(videoID = videoID)
            _videoPlayerViewState.update { it.copy(videoDetail = videoDetail ?: VideoDetails()) }
            delay(500)
            _videoPlayerViewState.update { it.copy(isLoadingDetail = false) }
        }
    }

    private fun handleIntent(intent: Intent?) {

        if (intent == null)
            return

        intent.extras?.let { intentBundle ->

            val videoID = intentBundle.getString(VideoPlayerActivity.VIDEO_ID) ?: ""
            val favorite = intentBundle.getBoolean(VideoPlayerActivity.FAVORITE)
            val videos = intentBundle.getBoolean(VideoPlayerActivity.VIDEOS)

            if (_videoPlayerViewState.value.videoID.isBlank()) {
                _videoPlayerViewState.update {
                    it.copy(
                        videoID = videoID,
                        favorite = favorite,
                        videos = videos
                    )
                }
                this.updatedVideoItemList()

                viewModelScope.launch {
                    delay(500)
                    _uiEvent.send(UIEvent.ScrollToIndex(index = _videoPlayerViewState.value.nowPlayingVideoIndex))
                }
                loadVideoDetail(videoID = videoID)
            } else {

                if (videoID.isNotBlank() && videoID != _videoPlayerViewState.value.videoID) {
                    _videoPlayerViewState.update { it.copy(videoID = videoID) }
                    loadVideoDetail(videoID = videoID)
                }

                if (favorite != _videoPlayerViewState.value.favorite) {
                    _videoPlayerViewState.update { it.copy(favorite = favorite) }
                }

                if (videos != _videoPlayerViewState.value.videos) {
                    _videoPlayerViewState.update { it.copy(videos = videos) }
                }

                this.updatedVideoItemList()

                viewModelScope.launch {
                    delay(500)
                    _uiEvent.send(UIEvent.ScrollToIndex(index = _videoPlayerViewState.value.nowPlayingVideoIndex))
                }
            }
        }
    }

    private fun updatedVideoItemList() {
        if (_videoPlayerViewState.value.videos) {
            val videoItemList = repository.getVideos()
            val nowPlayingIndex = videoItemList.indexOfFirst { it.id == _videoPlayerViewState.value.videoID  }
            _videoPlayerViewState.update { it.copy(items = videoItemList, nowPlayingVideoIndex = if (nowPlayingIndex < 0) 0 else nowPlayingIndex) }
        } else {
            val favoriteVideoItemList = repository.getFavoriteVideos()
            val nowPlayingIndex = favoriteVideoItemList.indexOfFirst { it.id == _videoPlayerViewState.value.videoID  }
            _videoPlayerViewState.update { it.copy(items = favoriteVideoItemList, nowPlayingVideoIndex = if (nowPlayingIndex < 0) 0 else nowPlayingIndex) }
        }
    }
}