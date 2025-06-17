package com.droiddevtips.admobnextgenads.feature.rewardedAds

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface RewardedAdNewsRepository {

    suspend fun loadNews(): List<RewardedAdListDisplayItem>

}