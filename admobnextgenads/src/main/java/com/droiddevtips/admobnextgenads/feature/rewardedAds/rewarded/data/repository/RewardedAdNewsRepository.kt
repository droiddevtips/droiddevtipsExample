package com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.repository

import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdListDisplayItem

/**
 * The rewarded ad news repository
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface RewardedAdNewsRepository {

    suspend fun loadNews(): List<RewardedAdListDisplayItem>

}