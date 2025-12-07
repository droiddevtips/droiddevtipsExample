package com.droiddevtips.floatingtabbarandpip.common.videoPlayer

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed interface UIEvent {
    data class ScrollToIndex(val index:Int): UIEvent
}