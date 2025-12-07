package com.droiddevtips.floatingtabbarandpip.common.videoList.data

/**
 * The video repository UI events
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed interface UIEvent {
    data class LaunchVideoActivity(val videoID:String, val favorite: Boolean = false, val videos: Boolean = false): UIEvent
}