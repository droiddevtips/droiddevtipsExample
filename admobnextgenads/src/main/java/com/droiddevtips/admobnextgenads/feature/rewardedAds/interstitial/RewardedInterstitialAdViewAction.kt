package com.droiddevtips.admobnextgenads.feature.rewardedAds.interstitial

import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdListDisplayItem

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed interface RewardedInterstitialAdViewAction {

//    data class ShowRewardedInterstitialAd(val premiumArticleData: RewardedAdListDisplayItem) : RewardedInterstitialAdViewAction
    data object ResetViewState : RewardedInterstitialAdViewAction
    data object ShowRewardedInterstitialAd : RewardedInterstitialAdViewAction
    data class DismissRewardedInterstitialAd(val rewardClaimed: Boolean = false) : RewardedInterstitialAdViewAction
    data class ShowRewardedInterstitialAdDialog(val premiumArticleData: RewardedAdListDisplayItem) : RewardedInterstitialAdViewAction
    data object DismissRewardedInterstitialAdDialog : RewardedInterstitialAdViewAction

}