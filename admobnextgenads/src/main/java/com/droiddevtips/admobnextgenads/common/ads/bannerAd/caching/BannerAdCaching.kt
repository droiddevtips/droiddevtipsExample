package com.droiddevtips.admobnextgenads.common.ads.bannerAd.caching

import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface BannerAdCaching {
    fun hasBannerAd(bannerAdUnit: NextGenAdUnit): Boolean
    fun getBannerAd(id: String): BannerAd?
    fun cacheBannerAds(id:String, bannerAds:BannerAd)
    fun removeCacheBannerAds(key:String)
}