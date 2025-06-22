package com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data

/**
 * The rewarded ad view action sealed interface
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed interface RewardedAdViewAction {

    data object LoadAndShowRewardedAd: RewardedAdViewAction
    data object OnRewardedAdCompleted: RewardedAdViewAction
    data object DismissRewardedAd: RewardedAdViewAction
    data object SubtractCredit: RewardedAdViewAction

}