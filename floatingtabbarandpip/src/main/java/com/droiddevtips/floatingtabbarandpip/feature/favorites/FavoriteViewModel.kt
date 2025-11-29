package com.droiddevtips.floatingtabbarandpip.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.floatingtabbarandpip.common.videoRepository.VideoRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

/**
 * The favorite view model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class FavoriteViewModel(private val repository: VideoRepository): ViewModel() {

    private val _favoriteViewState = MutableStateFlow(FavoriteViewState())
    val profileViewState = _favoriteViewState.asStateFlow()

    init {
        loadFavoriteVideos()
    }

    fun loadFavoriteVideos() {
        viewModelScope.launch {
            val favoriteVideos = repository.getFavoriteVideos()
            _favoriteViewState.update { it.copy(videoList = favoriteVideos) }
            delay(2.seconds)
            _favoriteViewState.update { it.copy(isLoading = false) }
        }
    }
}