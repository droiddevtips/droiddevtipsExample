package com.droiddevtips.musicplayer.ui.mainView.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */

//TODO: Swap show 'play' and 'pause' button with a single Boolean state

@Parcelize
data class MusicPlayerViewState(
    val currentlyPlaying: MusicTrack? = null,
    val showAudioVisualizer: Boolean = false,
    val isPlaying: Boolean = false,
//    val showPlayButton: Boolean = true,
//    val showPauseButton: Boolean = false,
    val showMiniPlayer: Boolean = false,
    val expandMiniPlayer: Boolean = false,
    val shuffled: Boolean = false,
    val muted: Boolean = false,
    val enablePreviousButton: Boolean = false,
    val enableNextButton: Boolean = true,
    val positionDisplayText: String = "",
    val totalDurationDisplayText: String = "",
    val percentage: Double = 0.0,
    val musicList: List<MusicTrack> = emptyList()
) : Parcelable