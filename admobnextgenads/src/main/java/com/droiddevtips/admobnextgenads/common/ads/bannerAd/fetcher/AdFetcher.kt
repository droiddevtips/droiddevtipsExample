package com.droiddevtips.admobnextgenads.common.ads.bannerAd.fetcher

import android.content.Context
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd
import com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface AdFetcher {
    fun fetchBannerAd(adUnit: NextGenAdUnit, bannerAdView: (BannerAd?) -> Unit = {})
    fun fetchNativeVideoAd(
        adUnit: NextGenAdUnit,
        muteVideo: Boolean,
        requestCustomVideoControl: Boolean = false,
        nativeVideoAd: (NativeAd?) -> Unit = {}
    )

    fun fetchNativeImageAd(
        adUnit: NextGenAdUnit,
        nativeVideoAd: (NativeAd?) -> Unit = {}
    )

    fun fetchNativeFullscreenAd(
        adUnit: NextGenAdUnit,
        nativeVideoAd: (NativeAd?) -> Unit = {}
    )

    fun fetchRewardedAd(
        adUnit: NextGenAdUnit,
        rewardedAd: (RewardedAd?) -> Unit = {}
    )

    fun fetchCollapsibleBannerAd(
        context: Context,
        adUnit: NextGenAdUnit,
        collapsibleType: CollapsibleType,
        bannerAdView: (BannerAd?) -> Unit = {}
    )

    fun fetchAnchoredBannerAd(
        context: Context,
        adUnit: NextGenAdUnit,
        bannerAdView: (BannerAd?) -> Unit = {}
    )

    fun fetchInterstitialAd(adUnit: NextGenAdUnit, interstitialAd: (InterstitialAd?) -> Unit = {})
    fun containsCachedBannerAd(adUnit: NextGenAdUnit): Boolean
    fun getCachedAdHeight(adUnit: NextGenAdUnit): Int
    fun clearAllCachedBanner()
}