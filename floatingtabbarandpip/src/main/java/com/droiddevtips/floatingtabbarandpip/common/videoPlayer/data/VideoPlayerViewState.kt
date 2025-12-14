package com.droiddevtips.floatingtabbarandpip.common.videoPlayer.data

import android.os.Parcelable
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoDetails
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoItem
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * The video player view state
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class VideoPlayerViewState(
    val videoID: String = "",
    val nowPlayingVideoIndex: Int = 0,
    val videos: Boolean = true,
    val favorite: Boolean = false,
    val isInPip: Boolean = false,
    val isLoading: Boolean = true,
    val isLoadingDetail: Boolean = true,
    val videoDetail: @RawValue VideoDetails = VideoDetails(),
    val showPipButton: Boolean = false,
    val playerState: PlayerConstants.PlayerState = PlayerConstants.PlayerState.UNKNOWN,
    val items: List<VideoItem> = emptyList()
) : Parcelable