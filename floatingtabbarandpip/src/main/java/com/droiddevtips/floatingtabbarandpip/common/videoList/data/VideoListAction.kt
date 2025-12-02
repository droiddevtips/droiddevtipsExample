package com.droiddevtips.floatingtabbarandpip.common.videoList.data

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed interface VideoListAction {
    data class ScrollPosition(val index:Int): VideoListAction
    data class LaunchYouTubePlayer(val videoID:String): VideoListAction
}