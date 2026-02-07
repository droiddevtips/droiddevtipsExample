package com.droiddevtips.musicplayer.mainView

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
sealed interface MusicPlayerAction {
    data class ChangeMusicTrack(val track: MusicTrack) : MusicPlayerAction
    data object ClearMusicTrack : MusicPlayerAction
}