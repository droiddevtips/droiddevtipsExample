package com.droiddevtips.admobnextgenads.feature.rewardedAds

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class RewardedAdViewState(
    val isLoading: Boolean = true,
    val credit: Int = 0,
    val newsList: List<RewardedAdListItem> = emptyList()
) : Parcelable
