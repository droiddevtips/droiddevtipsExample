@file:Suppress("UNCHECKED_CAST")

package com.droiddevtips.floatingtabbarandpip.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.droiddevtips.floatingtabbarandpip.common.videoRepository.VideoRepository

/**
 * The favorite view model factory
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class FavoriteViewModelFactory(private val repository: VideoRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(
                repository = repository
            ) as T
        }

        throw IllegalArgumentException("Not ${FavoriteViewModel::class.simpleName} class")
    }

}