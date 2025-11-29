package com.droiddevtips.floatingtabbarandpip.extensions

import com.droiddevtips.floatingtabbarandpip.common.videoItem.VideoItem
import java.util.UUID

/**
 * The string extensions
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
fun String.asVideoItem(): VideoItem = VideoItem(
    id = UUID.randomUUID().toString(),
    videoUrl = "https://www.youtube.com/watch?v=$this",
    videoThumbnailUrl = "https://img.youtube.com/vi/$this/maxresdefault.jpg"
)