@file:Suppress("UNCHECKED_CAST")

package com.droiddevtips.floatingtabbarandpip.common.videoPlayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.videoRepository.VideoRepository

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class VideoPlayerViewModelFactory(private val repository: VideoRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(VideoPlayerViewModel::class.java)) {
            return VideoPlayerViewModel(
                repository = repository
            ) as T
        }

        throw IllegalArgumentException("Not ${VideoPlayerViewModel::class.simpleName} class")
    }
}