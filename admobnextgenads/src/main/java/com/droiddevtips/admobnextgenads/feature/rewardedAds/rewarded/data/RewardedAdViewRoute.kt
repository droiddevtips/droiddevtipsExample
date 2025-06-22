package com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data

/**
 * The rewarded ad view route sealed class
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed class RewardedAdViewRoute(val route:String) {
    data object RewardedAdsArticleList: RewardedAdViewRoute(route = "articleList")
    data object RewardedAdsArticleDetail: RewardedAdViewRoute(route = "articleDetail")
}
