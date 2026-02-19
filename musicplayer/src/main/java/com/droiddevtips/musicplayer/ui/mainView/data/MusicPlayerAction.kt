package com.droiddevtips.musicplayer.ui.mainView.data

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
sealed interface MusicPlayerAction {
    data object Play : MusicPlayerAction
    data object Pause : MusicPlayerAction
    data object Previous : MusicPlayerAction
    data object Next : MusicPlayerAction
    data object CloseMiniPlayer : MusicPlayerAction
    data class ToggleMiniPlayerView(val expanded: Boolean = false) : MusicPlayerAction
    data class ChangeMusicTrack(val track: MusicTrack) : MusicPlayerAction
}