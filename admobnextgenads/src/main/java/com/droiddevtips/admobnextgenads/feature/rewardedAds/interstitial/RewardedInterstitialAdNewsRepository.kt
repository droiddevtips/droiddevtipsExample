package com.droiddevtips.admobnextgenads.feature.rewardedAds.interstitial

import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdListDisplayItem

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface RewardedInterstitialAdNewsRepository {

    suspend fun loadRewardedInterstitialDummyNews(): List<RewardedAdListDisplayItem>

}