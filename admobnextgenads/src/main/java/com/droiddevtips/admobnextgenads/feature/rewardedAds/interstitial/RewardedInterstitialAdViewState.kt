package com.droiddevtips.admobnextgenads.feature.rewardedAds.interstitial

import android.os.Parcelable
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdListDisplayItem
import com.google.android.libraries.ads.mobile.sdk.rewardedinterstitial.RewardedInterstitialAd
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class RewardedInterstitialAdViewState(
    val isLoading: Boolean = true,
    val navigateToDetail: Boolean = false,
    val premiumArticleData: RewardedAdListDisplayItem? = null,
    @IgnoredOnParcel
    val isLoadingAd: Boolean = false,
    @IgnoredOnParcel
    val showRewardedInterstitialDialog: Boolean = false,
    @IgnoredOnParcel
    val showRewardedInterstitial: Boolean = false,
    @IgnoredOnParcel
    val rewardedInterstitialAdView: RewardedInterstitialAd? = null,
    val newsList: List<RewardedAdListDisplayItem> = emptyList()
): Parcelable
