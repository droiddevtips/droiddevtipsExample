package com.droiddevtips.admobnextgenads.common.ads.bannerAd.fetcher

import android.content.Context
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
import com.google.android.libraries.ads.mobile.sdk.common.VideoOptions
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAd
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdLoader
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdLoaderCallback
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdRequest
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
                    if (adUnit.key.isNotBlank()) {
                        cacheBannerAds(id = adUnit.key, bannerAds = ad)
                    }

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

    override fun fetchNativeVideoAd(
        adUnit: NextGenAdUnit,
        muteVideo: Boolean,
        requestCustomVideoControl: Boolean,
        nativeVideoAd: (NativeAd?) -> Unit
    ) {
        val videoOptions = VideoOptions.Builder().setStartMuted(startMuted = muteVideo).setCustomControlsRequested(requestCustomVideoControl).build()
        val nativeAdVideoRequest = NativeAdRequest.Builder(adUnit.id, listOf(NativeAd.NativeAdType.NATIVE))
            .setVideoOptions(videoOptions)
            .build()

        val nativeAdVideoCallback = object: NativeAdLoaderCallback {

            override fun onNativeAdLoaded(nativeAd: NativeAd) {
                super.onNativeAdLoaded(nativeAd)
                nativeVideoAd(nativeAd)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                nativeVideoAd(null)
            }
        }

        NativeAdLoader.load(nativeAdVideoRequest, nativeAdVideoCallback)
    }

    override fun fetchNativeImageAd(
        adUnit: NextGenAdUnit,
        nativeVideoAd: (NativeAd?) -> Unit
    ) {
        val nativeAdVideoRequest = NativeAdRequest.Builder(adUnit.id, listOf(NativeAd.NativeAdType.NATIVE))
            .build()

        val nativeAdVideoCallback = object: NativeAdLoaderCallback {

            override fun onNativeAdLoaded(nativeAd: NativeAd) {
                super.onNativeAdLoaded(nativeAd)
                nativeVideoAd(nativeAd)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                nativeVideoAd(null)
            }
        }

        NativeAdLoader.load(nativeAdVideoRequest, nativeAdVideoCallback)
    }

    override fun fetchCollapsibleBannerAd(
        context: Context,
        adUnit: NextGenAdUnit,
        collapsibleType: CollapsibleType,
        bannerAdView: (BannerAd?) -> Unit
    ) {
        if (adUnit.id.isBlank()) {
            bannerAdView(null)
            return
        }

        val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context = context, width = 360)

        val googleExtraBundle = Bundle()
        googleExtraBundle.putString(
            "collapsible",
            collapsibleType.toString().lowercase()
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

    override fun fetchAnchoredBannerAd(
        context: Context,
        adUnit: NextGenAdUnit,
        bannerAdView: (BannerAd?) -> Unit
    ) {

        if (adUnit.id.isBlank()) {
            bannerAdView(null)
            return
        }

        val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context = context, width = MobileAdsManager.deviceScreenWidth)

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