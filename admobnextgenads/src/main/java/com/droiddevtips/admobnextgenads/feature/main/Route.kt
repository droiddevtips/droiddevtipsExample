package com.droiddevtips.admobnextgenads.feature.main

/**
 * The nav controller routes
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed class Route(val route: String) {
    data object Home: Route("home")
    data object BannerAd: Route("bannerAd")
//    data object Collapsible: Route("collapsible")
//    data object FixedSize: Route("fixedSize")
    data object InterstitialAds: Route("interstitialAds")
    data object NativeAds: Route("nativeAds")
    data object RewardedAds: Route("rewardedAds")
    data object RewardedInterstitialAds: Route("rewardedInterstitialAds")
}