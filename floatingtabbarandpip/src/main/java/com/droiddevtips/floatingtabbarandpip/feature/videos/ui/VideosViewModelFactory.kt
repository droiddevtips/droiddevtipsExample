@file:Suppress("UNCHECKED_CAST")

package com.droiddevtips.floatingtabbarandpip.feature.videos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.videoRepository.VideoRepository

/**
 * The video view model factory
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class VideosViewModelFactory(private val repository: VideoRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(VideosViewModel::class.java)) {
            return VideosViewModel(
                repository = repository
            ) as T
        }

        throw IllegalArgumentException("Not ${VideosViewModel::class.simpleName} class")
    }
}