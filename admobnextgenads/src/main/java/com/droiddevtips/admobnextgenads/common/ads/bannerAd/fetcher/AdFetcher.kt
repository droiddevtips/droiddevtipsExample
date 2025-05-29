package com.droiddevtips.admobnextgenads.common.ads.bannerAd.fetcher

import android.content.Context
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface AdFetcher {
    fun fetchBannerAd(adUnit: NextGenAdUnit, bannerAdView: (BannerAd?) -> Unit = {})
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