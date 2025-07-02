@file:Suppress("UNCHECKED_CAST")

package com.droiddevtips.admobnextgenads.feature.rewardedAds.interstitial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.fetcher.AdFetcher

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class RewardedInterstitialAdViewModelFactory(private val repository: RewardedInterstitialAdNewsRepository, private val adFetcher: AdFetcher): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(RewardedInterstitialViewModel::class.java)) {

            return RewardedInterstitialViewModel(
                savedStateHandle = extras.createSavedStateHandle(),
                adFetcher = adFetcher,
                repository = repository
            ) as T

        }

        throw IllegalArgumentException("Not ${RewardedInterstitialViewModel::class.simpleName} class")
    }

}