package com.droiddevtips.floatingtabbarandpip.feature.favorites

import android.os.Parcelable
import com.droiddevtips.floatingtabbarandpip.common.videoItem.VideoItem
import kotlinx.parcelize.Parcelize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class FavoriteViewState(
    val isLoading: Boolean = true,
    val videoList: List<VideoItem> = emptyList()
) : Parcelable
