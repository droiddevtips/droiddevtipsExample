package com.droiddevtips.floatingtabbarandpip.feature.videos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.floatingtabbarandpip.common.videoRepository.VideoRepository
import com.droiddevtips.floatingtabbarandpip.feature.videos.data.VideosViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

/**
 * The video view model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class VideosViewModel(private val repository: VideoRepository): ViewModel() {

    private val _videosViewState = MutableStateFlow(VideosViewState())
    val videosViewState = _videosViewState.asStateFlow()

    init {
        loadVideos()
    }

    fun loadVideos() {
        viewModelScope.launch {
            val videos = repository.getVideos()
            _videosViewState.update { it.copy(videoList = videos) }
            delay(2.seconds)
            _videosViewState.update { it.copy(isLoading = false) }
        }
    }
}