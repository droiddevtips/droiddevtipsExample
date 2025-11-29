package com.droiddevtips.floatingtabbarandpip.common.videoList.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class VideoListViewState(
    val isLoading: Boolean = true,
    val visibleIndex: Int = 0,
    val videoList: List<VideoItem> = emptyList()
) : Parcelable