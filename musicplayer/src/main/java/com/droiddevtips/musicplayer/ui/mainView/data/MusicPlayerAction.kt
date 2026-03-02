package com.droiddevtips.musicplayer.ui.mainView.data

/**
 * The music player action sealed interface
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
sealed interface MusicPlayerAction {
    data object Play : MusicPlayerAction
    data class Pause(val fromMusicPlayerView: Boolean = false) : MusicPlayerAction
    data object StopPlayerIfNeeded : MusicPlayerAction
    data object Previous : MusicPlayerAction
    data object Next : MusicPlayerAction
    data object CloseMiniPlayer : MusicPlayerAction
    data class ToggleMiniPlayerView(val expanded: Boolean = false) : MusicPlayerAction
    data class ChangeMusicTrack(val track: MusicTrack) : MusicPlayerAction
}