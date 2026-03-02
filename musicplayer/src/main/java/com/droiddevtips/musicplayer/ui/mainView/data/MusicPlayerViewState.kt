package com.droiddevtips.musicplayer.ui.mainView.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The music player view state data model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Parcelize
data class MusicPlayerViewState(
    val currentlyPlaying: MusicTrack? = null,
    val showAudioVisualizer: Boolean = false,
    val isPlaying: Boolean = false,
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