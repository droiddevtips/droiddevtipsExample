package com.droiddevtips.admobnextgenads.feature.rewardedAds.interstitial.data

import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdListDisplayItem

/**
 * The rewarded interstitial ad view state MVI action sealed interface
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed interface RewardedInterstitialAdViewAction {

    data object ResetViewState : RewardedInterstitialAdViewAction
    data object ShowRewardedInterstitialAd : RewardedInterstitialAdViewAction
    data class DismissRewardedInterstitialAd(val rewardClaimed: Boolean = false) : RewardedInterstitialAdViewAction
    data class ShowRewardedInterstitialAdDialog(val premiumArticleData: RewardedAdListDisplayItem) : RewardedInterstitialAdViewAction
    data object DismissRewardedInterstitialAdDialog : RewardedInterstitialAdViewAction
}