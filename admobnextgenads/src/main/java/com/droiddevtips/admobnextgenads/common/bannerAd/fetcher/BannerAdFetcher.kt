package com.droiddevtips.admobnextgenads.common.bannerAd.fetcher

import com.droiddevtips.admobnextgenads.common.NextGenAdUnit
import com.droiddevtips.admobnextgenads.common.bannerAd.caching.BannerAdCaching
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface BannerAdFetcher {

    val bannerAdsCaching: BannerAdCaching
    fun fetchBannerAd(adUnit: NextGenAdUnit, bannerAdView: (BannerAd?) -> Unit = {})
    fun getBannerAd(id: String): BannerAd?

}