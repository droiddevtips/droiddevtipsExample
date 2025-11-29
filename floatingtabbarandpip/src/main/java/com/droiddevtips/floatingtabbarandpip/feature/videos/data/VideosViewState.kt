package com.droiddevtips.floatingtabbarandpip.feature.videos.data

import android.os.Parcelable
import com.droiddevtips.floatingtabbarandpip.common.videoItem.VideoItem
import kotlinx.parcelize.Parcelize

/**
 * The videos view state data model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class VideosViewState(
    val isLoading: Boolean = true,
    val videoList: List<VideoItem> = emptyList()
) : Parcelable