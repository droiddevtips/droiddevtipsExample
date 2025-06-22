package com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data

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
    val imageID: String,
    val title: String,
    val titleID: String,
    val description: String,
    val premium: Boolean = false
) : Parcelable