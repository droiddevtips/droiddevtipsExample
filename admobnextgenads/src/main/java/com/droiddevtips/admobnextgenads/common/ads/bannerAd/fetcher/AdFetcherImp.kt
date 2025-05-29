package com.droiddevtips.admobnextgenads.common.ads.bannerAd.fetcher

import android.os.Bundle
import com.droiddevtips.admobnextgenads.common.ads.MobileAdsManager
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.caching.BannerAdCaching
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.caching.BannerAdCachingImp
import com.google.android.libraries.ads.mobile.sdk.banner.AdSize
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRequest
import com.google.android.libraries.ads.mobile.sdk.common.AdLoadCallback
import com.google.android.libraries.ads.mobile.sdk.common.AdRequest
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class AdFetcherImp : AdFetcher, BannerAdCaching by BannerAdCachingImp() {

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
            .putCustomTargeting("key1", "value1")
            .putCustomTargeting("key2", "value2")

            .setContentUrl(adUnit.contentUrl) // For the content url
            .build()

        CoroutineScope(Dispatchers.IO).launch {

            BannerAd.load(adRequest = adRequest, object : AdLoadCallback<BannerAd> {

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

    override fun fetchCollapsibleBannerAd(
        adUnit: NextGenAdUnit,
        bannerAdView: (BannerAd?) -> Unit
    ) {
        if (adUnit.id.isBlank()) {
            bannerAdView(null)
            return
        }

        val adSize = AdSize.getInlineAdaptiveBannerAdSize(
            MobileAdsManager.deviceScreenWidth,
            maxHeight = 600
        )

        val googleExtraBundle = Bundle()
        googleExtraBundle.putString(
            "collapsible",
            "bottom"
        ) // Must be provided in order to get an collapsible ads

        val adRequest = BannerAdRequest.Builder(adUnit.id, adSize)
            .setGoogleExtrasBundle(googleExtraBundle)
            // In case you want to provide some custom targeting
            .putCustomTargeting("key1", "value1")
            .putCustomTargeting("key2", "value2")

            .setContentUrl(adUnit.contentUrl) // For the content url
            .build()

        CoroutineScope(Dispatchers.IO).launch {

            BannerAd.load(adRequest = adRequest, object : AdLoadCallback<BannerAd> {

                override fun onAdLoaded(ad: BannerAd) {
                    super.onAdLoaded(ad)
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

    override fun fetchInterstitialAd(
        adUnit: NextGenAdUnit,
        interstitialAd: (InterstitialAd?) -> Unit
    ) {

        val interstitialRequest =
            AdRequest.Builder(adUnit.id)
                .setContentUrl(adUnit.contentUrl) // your custom content url
                .putCustomTargeting("[your custom key]", "[your custom value]") // your custom params
                .build()

        InterstitialAd.load(interstitialRequest, object : AdLoadCallback<InterstitialAd> {

            override fun onAdLoaded(ad: InterstitialAd) {
                super.onAdLoaded(ad)
                interstitialAd(ad)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                println("Failed to load interstitial ad, cause: ${adError.message}")
                interstitialAd(null)
            }
        })
    }

    override fun containsCachedBannerAd(adUnit: NextGenAdUnit): Boolean {
        return hasBannerAd(bannerAdUnit = adUnit)
    }

    override fun getCachedAdHeight(adUnit: NextGenAdUnit): Int = getBannerAd(id = adUnit.id)?.getAdSize()?.height ?:run {
        0
    }

    override fun clearAllCachedBanner() {
        flushCache()
    }
}