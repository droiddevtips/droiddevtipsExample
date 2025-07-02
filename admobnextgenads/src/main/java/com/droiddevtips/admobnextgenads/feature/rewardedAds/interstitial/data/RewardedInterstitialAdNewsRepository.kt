package com.droiddevtips.admobnextgenads.feature.rewardedAds.interstitial.data

import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdListDisplayItem

/**
 * The rewarded interstitial ad news repository interface
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface RewardedInterstitialAdNewsRepository {

    suspend fun loadRewardedInterstitialDummyNews(): List<RewardedAdListDisplayItem>

}