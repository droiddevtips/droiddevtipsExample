package com.droiddevtips.musicplayer.mainView

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Parcelize
data class MusicPlayerViewState(val currentlyPlaying: MusicTrack? = null, val musicList: List<MusicTrack> = emptyList()): Parcelable
