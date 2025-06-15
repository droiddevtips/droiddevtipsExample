package com.droiddevtips.admobnextgenads.feature.rewardedAds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class RewardedAdViewModelFactory(private val repository: RewardedAdNewsRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(RewardedAdViewModel::class.java)) {

            return RewardedAdViewModel(savedStateHandle = extras.createSavedStateHandle(), repository = repository) as T

        }

        throw IllegalArgumentException("Not ${RewardedAdViewModel::class.simpleName} class")
    }
}