package com.droiddevtips.admobnextgenads.common.ads.bannerAd.caching

import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class BannerAdCachingImp : BannerAdCaching {

    private val bannerAdCache = ConcurrentHashMap<String, BannerAd>()

    override fun getBannerAd(id: String): BannerAd? = bannerAdCache[id]

    override fun hasBannerAd(bannerAdUnit: NextGenAdUnit): Boolean = bannerAdCache.containsKey(bannerAdUnit.key)

    override fun cacheBannerAds(id: String, bannerAds: BannerAd) {
        bannerAdCache[id] = bannerAds
    }

    override fun removeCacheBannerAds(key: String) {
        bannerAdCache.remove(key = key)
    }

    override fun flushCache() {
        bannerAdCache.clear()
    }
}