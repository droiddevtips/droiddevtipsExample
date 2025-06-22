package com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data

import android.os.Parcelable
import com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class RewardedAdViewState(
    val isLoading: Boolean = true,
    @IgnoredOnParcel
    val isLoadingAd: Boolean = false,
    val showRewardedAdButton: Boolean = false,
    @IgnoredOnParcel
    val showRewardedAd: Boolean = false,
    @IgnoredOnParcel
    val rewardedAdView: RewardedAd? = null,
    val credit: Int = 0,
    val newsList: List<RewardedAdListDisplayItem> = emptyList()
) : Parcelable