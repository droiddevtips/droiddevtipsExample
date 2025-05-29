package com.droiddevtips.admobnextgenads.common.ads.bannerAd.fetcher

import com.droiddevtips.admobnextgenads.common.ads.MobileAdsManager
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.caching.BannerAdCaching
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.caching.BannerAdCachingImp
import com.google.android.libraries.ads.mobile.sdk.banner.AdSize
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRequest
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class BannerAdFetcherImp : BannerAdFetcher, BannerAdCaching by BannerAdCachingImp() {

    override fun fetchBannerAd(adUnit: NextGenAdUnit, bannerAdView: (BannerAd?) -> Unit) {

        if (adUnit.id.isBlank()) {
            bannerAdView(null)
            return
        }

        if (containsCachedBannerAd(adUnit = adUnit)) {
            val cachedBannerAd = getBannerAd(id = adUnit.key)
            bannerAdView(cachedBannerAd)
            return
        }

        val adSize = AdSize.getInlineAdaptiveBannerAdSize(
            MobileAdsManager.deviceScreenWidth,
            maxHeight = 600
        )
        val adRequest = BannerAdRequest.Builder(adUnit.id, adSize)
            // In case you want to provide some custom targeting
            .putCustomTargeting("key1","value1")
            .putCustomTargeting("key2","value2")

            .setContentUrl(adUnit.contentUrl) // For the content url
            .build()

        CoroutineScope(Dispatchers.IO).launch {

            BannerAd.load(adRequest = adRequest, object : AdLoadCallback<BannerAd>{

                override fun onAdLoaded(ad: BannerAd) {
                    super.onAdLoaded(ad)
                    cacheBannerAds(id = adUnit.key, bannerAds = ad)
                    CoroutineScope(Dispatchers.Main).launch {
                        bannerAdView(ad)
                    }
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    CoroutineScope(Dispatchers.Main).launch {
                        bannerAdView(null)
                    }
                }
            })
        }
    }

    override fun containsCachedBannerAd(adUnit: NextGenAdUnit): Boolean {
        return hasBannerAd(bannerAdUnit = adUnit)
    }
}