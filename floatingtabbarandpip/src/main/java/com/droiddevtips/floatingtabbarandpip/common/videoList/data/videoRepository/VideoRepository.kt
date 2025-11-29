package com.droiddevtips.floatingtabbarandpip.common.videoList.data.videoRepository

import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoItem

/**
 * The videos repository
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface VideoRepository {
    fun getVideos(): List<VideoItem>
    fun getFavoriteVideos(): List<VideoItem>
}