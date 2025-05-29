package com.droiddevtips.admobnextgenads.common.ads.bannerAd.fetcher

import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface BannerAdFetcher {
    fun fetchBannerAd(adUnit: NextGenAdUnit, bannerAdView: (BannerAd?) -> Unit = {})
    fun containsCachedBannerAd(adUnit: NextGenAdUnit): Boolean
}