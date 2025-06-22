package com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.fetcher.AdFetcher
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.repository.RewardedAdNewsRepository

/**
 * The rewarded ad view model factory
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class RewardedAdViewModelFactory(private val repository: RewardedAdNewsRepository, private val adFetcher: AdFetcher): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(RewardedAdViewModel::class.java)) {

            return RewardedAdViewModel(
                savedStateHandle = extras.createSavedStateHandle(),
                adFetcher = adFetcher,
                repository = repository
            ) as T

        }

        throw IllegalArgumentException("Not ${RewardedAdViewModel::class.simpleName} class")
    }
}