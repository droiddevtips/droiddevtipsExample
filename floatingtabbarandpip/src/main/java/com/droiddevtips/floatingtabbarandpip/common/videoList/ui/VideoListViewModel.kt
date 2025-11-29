package com.droiddevtips.floatingtabbarandpip.common.videoList.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoListViewState
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoListAction
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.videoRepository.VideoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
abstract class VideoListViewModel(
    protected val favorite: Boolean,
    protected val repository: VideoRepository
): ViewModel() {

    private val _videosViewState = MutableStateFlow(VideoListViewState())
    val videoViewState: StateFlow<VideoListViewState>
        get() = _videosViewState.asStateFlow()

    init {
        loadVideos()
    }

    fun loadVideos() {
        viewModelScope.launch {
            val videos = if (favorite) repository.getFavoriteVideos() else repository.getVideos()
            _videosViewState.update { it.copy(videoList = videos) }
            delay(2.seconds)
            _videosViewState.update { it.copy(isLoading = false) }
        }
    }

    fun performAction(action: VideoListAction) {
        when(action) {
            is VideoListAction.ScrollPosition -> {
                _videosViewState.update { it.copy(visibleIndex = action.index) }
            }
        }
    }
}