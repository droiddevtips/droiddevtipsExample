package com.droiddevtips.floatingtabbarandpip.common.videoItem

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The video item data model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class VideoItem(val id: String, val videoUrl: String, val videoThumbnailUrl: String) : Parcelable
