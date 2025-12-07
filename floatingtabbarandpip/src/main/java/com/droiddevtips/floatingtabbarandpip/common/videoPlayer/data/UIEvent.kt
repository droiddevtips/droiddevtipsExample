package com.droiddevtips.floatingtabbarandpip.common.videoPlayer.data

/**
 * The video player UI event data model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed interface UIEvent {
    data class ScrollToIndex(val index:Int): UIEvent
}