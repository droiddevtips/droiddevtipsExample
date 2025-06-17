package com.droiddevtips.admobnextgenads.feature.rewardedAds

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * This is the rewarded ad list item data model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class RewardedAdListDisplayItem(
    val image: Int,
    val title: String,
    val description: String,
    val premium: Boolean = false
) : Parcelable
