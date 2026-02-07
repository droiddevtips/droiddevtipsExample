package com.droiddevtips.musicplayer.mainView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.musicplayer.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class MusicPlayerViewModel : ViewModel() {

    private val _musicPlayerViewState = MutableStateFlow(MusicPlayerViewState())
    val musicPlayerViewState: StateFlow<MusicPlayerViewState>
        get() = _musicPlayerViewState.asStateFlow().onStart {
            _musicPlayerViewState.update { it.copy(musicList = musicList) }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MusicPlayerViewState())


    fun performAction(action: MusicPlayerAction) {

        when (action) {
            is MusicPlayerAction.ChangeMusicTrack -> TODO()
            MusicPlayerAction.ClearMusicTrack -> TODO()
        }
    }

    private val musicList: List<MusicTrack> = listOf(
        MusicTrack(
            song = "Alone",
            composer = "Alex Productions",
            songFile = R.raw.alone,
            thumbnail = R.mipmap.alone,
            credit = "Music powered by BreakingCopyright"
        ),
        MusicTrack(
            song = "Filaments",
            composer = "Scott Buckley",
            songFile = R.raw.filaments,
            thumbnail = R.mipmap.filaments_thumb,
            credit = "Music powered by BreakingCopyright"
        ),
        MusicTrack(
            song = "Dragon Castle",
            composer = "Makai Symphony",
            songFile = R.raw.dragon_castle,
            thumbnail = R.mipmap.dragon_castle_thumb,
            credit = "Music powered by BreakingCopyright"
        ),
        MusicTrack(
            song = "Way Home",
            composer = "Tokyo Music Walker",
            songFile = R.raw.way_home,
            thumbnail = R.mipmap.way_home_thumb,
            credit = "Music powered by BreakingCopyright"
        ),
        MusicTrack(
            song = "Indian Fusion",
            composer = "Shahed",
            songFile = R.raw.indian_fusion,
            thumbnail = R.mipmap.indian_fusion_thumb,
            credit = "Music powered by BreakingCopyright"
        ),
        MusicTrack(
            song = "The Epic Hero",
            composer = "Keys Of Moon",
            songFile = R.raw.epic_hero,
            thumbnail = R.mipmap.epic_hero_thumb,
            credit = "Music powered by BreakingCopyright"
        )
    )
}