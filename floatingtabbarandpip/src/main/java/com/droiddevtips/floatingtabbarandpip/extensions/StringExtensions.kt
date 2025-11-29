package com.droiddevtips.floatingtabbarandpip.extensions

import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoItem
import java.util.UUID

/**
 * The string extensions
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
fun String.asVideoItem(favorite: Boolean): VideoItem = VideoItem(
    id = UUID.randomUUID().toString(),
    favorite = favorite,
    videoUrl = "https://www.youtube.com/watch?v=$this",
    videoThumbnailUrl = "https://img.youtube.com/vi/$this/maxresdefault.jpg"
)