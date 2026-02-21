package com.droiddevtips.musicplayer.ui

import android.app.Application
import android.content.ComponentName
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.droiddevtips.musicplayer.R
import com.droiddevtips.musicplayer.core.MusicPlayerService
import com.droiddevtips.musicplayer.extensions.asMediaItemList
import com.droiddevtips.musicplayer.ui.mainView.data.MusicPlayerAction
import com.droiddevtips.musicplayer.ui.mainView.data.MusicPlayerViewState
import com.droiddevtips.musicplayer.ui.mainView.data.MusicTrack
import com.google.common.util.concurrent.MoreExecutors
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class MusicPlayerViewModel(application: Application) : AndroidViewModel(application),
    Player.Listener {

    private var mediaController: MediaController? = null
    private var sessionToken: SessionToken? = null
    private val _musicPlayerViewState = MutableStateFlow(MusicPlayerViewState())
    val musicPlayerViewState: StateFlow<MusicPlayerViewState>
        get() = _musicPlayerViewState.asStateFlow().onStart {
            _musicPlayerViewState.update { it.copy(musicList = musicList) }
        }.stateIn(
            viewModelScope, SharingStarted.Companion.WhileSubscribed(5000),
            MusicPlayerViewState()
        )

    init {
        sessionToken = SessionToken(
            application.applicationContext, ComponentName(
                application.applicationContext,
                MusicPlayerService::class.java
            )
        ).also { token ->
            val controller =
                MediaController.Builder(application.applicationContext, token).buildAsync()
            controller.addListener({
                mediaController = controller.get().apply {
                    setMediaItems(musicList.asMediaItemList())
                    addListener(this@MusicPlayerViewModel)
                }
            }, MoreExecutors.directExecutor())
        }

        trackProgress()
    }

    private fun trackProgress() {

        viewModelScope.launch {
            while (isActive) {
                mediaController?.let { player ->

                    if (player.isPlaying) {
                        val currentPosition = (player.currentPosition / 1000.00) / 60.00
                        val totalDuration = (player.duration.coerceAtLeast(0L) / 1000.00) / 60.00

                        val currentPositionText = "%.2f".format(currentPosition).replace(".", ":")
                        val totalDurationString = "%.2f".format(totalDuration).replace(".", ":")
                        val percentage =
                            if (currentPosition == 0.00) 0.0 else (currentPosition / totalDuration)

                        _musicPlayerViewState.update {
                            it.copy(
                                positionDisplayText = currentPositionText,
                                totalDurationDisplayText = totalDurationString,
                                percentage = percentage
                            )
                        }
                    }
                }
                delay(1.seconds)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        mediaController?.apply {
            if (isPlaying)
                stop()

            release()
        }

        mediaController = null
        sessionToken = null
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)

        _musicPlayerViewState.update { state ->
            state.copy(
                isPlaying = isPlaying,
                showMiniPlayer = if (_musicPlayerViewState.value.showMiniPlayer) true else isPlaying,
                enablePreviousButton = mediaController?.hasPreviousMediaItem() ?: false,
                enableNextButton = mediaController?.hasNextMediaItem() ?: false
            )
        }
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)

        val newMediaItem = mediaItem ?: return
        musicList.asSequence().filter { it.id == newMediaItem.mediaId }
            .firstOrNull()?.let { newTrack ->
                _musicPlayerViewState.update { it.copy(currentlyPlaying = newTrack, showAudioVisualizer = true) }
            }

    }

    fun performAction(action: MusicPlayerAction) {

        when (action) {
            is MusicPlayerAction.ChangeMusicTrack -> {
                val index = musicList.indexOf(action.track)
                _musicPlayerViewState.update { it.copy(currentlyPlaying = action.track, showAudioVisualizer = false) }
                mediaController?.apply {
                    seekTo(index, 0)
                    play()
                }
            }

            is MusicPlayerAction.Pause -> {
                mediaController?.apply {
                    if (isPlaying) {
                        pause()
                        if (action.fromMusicPlayerView) {
                            _musicPlayerViewState.update { state ->
                                state.copy(
                                    isPlaying = isPlaying,
//                                    showPauseButton = false,
//                                    showPlayButton = true,
                                    showMiniPlayer = false,
                                    showAudioVisualizer = false
                                )
                            }
                        } else {
                            _musicPlayerViewState.update { state ->
                                state.copy(
                                    isPlaying = isPlaying,
//                                    showPauseButton = false,
//                                    showPlayButton = true
                                )
                            }
                        }
                    }
                }
            }

            is MusicPlayerAction.Play -> {
                mediaController?.apply {
                    if (!isPlaying) {
                        play()
                        _musicPlayerViewState.update { state ->
                            state.copy(
//                                showPauseButton = true,
//                                showPlayButton = false,
                                isPlaying = isPlaying,
                                showMiniPlayer = true
                            )
                        }
                    }
                }
            }

            MusicPlayerAction.Next -> {
                mediaController?.let { player ->
                    if (player.hasNextMediaItem()) {
                        mediaController?.currentMediaItem?.let { currentMediaItem ->
                            musicList.asSequence().filter { it.id == currentMediaItem.mediaId }
                                .firstOrNull()?.let { newTrack ->
                                    val index = musicList.indexOf(newTrack)
                                    if (index != -1) {
                                        mediaController?.seekTo(index + 1, 0)
                                    }
                                }
                        }
                    }
                }
            }

            MusicPlayerAction.Previous -> {
                mediaController?.let { player ->
                    if (player.hasPreviousMediaItem()) {
                        mediaController?.currentMediaItem?.let { currentMediaItem ->
                            musicList.asSequence().filter { it.id == currentMediaItem.mediaId }
                                .firstOrNull()?.let { previousTrack ->
                                    val index = musicList.indexOf(previousTrack)
                                    if (index != -1) {
                                        mediaController?.seekTo(index - 1, 0)
                                    }
                                }
                        }
                    }
                }
            }

            MusicPlayerAction.CloseMiniPlayer -> closeMiniPlayer()

            is MusicPlayerAction.ToggleMiniPlayerView -> {
                _musicPlayerViewState.update { it.copy(expandMiniPlayer = !_musicPlayerViewState.value.expandMiniPlayer) }
            }

            is MusicPlayerAction.StopPlayerIfNeeded -> mediaController?.apply {
                if (!isPlaying) {
                    stop()
                    _musicPlayerViewState.update { it.copy(currentlyPlaying = null, showAudioVisualizer = false) }
                }
            }
        }
    }

    private fun closeMiniPlayer() {
        mediaController?.apply {
            if (isPlaying) {
                stop()
            }
        }
        _musicPlayerViewState.update {
            it.copy(
                showMiniPlayer = false,
                currentlyPlaying = null
            )
        }
    }

    private val musicList: List<MusicTrack> = listOf(
        MusicTrack(
            id = "1",
            song = "Alone",
            composer = "Alex Productions",
            songFile = R.raw.alone,
            thumbnail = R.mipmap.alone,
            thumbnailLarge = R.drawable.alone_large,
            credit = "Music powered by BreakingCopyright"
        ),
        MusicTrack(
            id = "2",
            song = "Filaments",
            composer = "Scott Buckley",
            songFile = R.raw.filaments,
            thumbnail = R.mipmap.filaments_thumb,
            thumbnailLarge = R.drawable.filaments_thumb_large,
            credit = "Music powered by BreakingCopyright"
        ),
        MusicTrack(
            id = "3",
            song = "Dragon Castle",
            composer = "Makai Symphony",
            songFile = R.raw.dragon_castle,
            thumbnail = R.mipmap.dragon_castle_thumb,
            thumbnailLarge = R.drawable.dragon_castle_thumb_large,
            credit = "Music powered by BreakingCopyright"
        ),
        MusicTrack(
            id = "4",
            song = "Way Home",
            composer = "Tokyo Music Walker",
            songFile = R.raw.way_home,
            thumbnail = R.mipmap.way_home_thumb,
            thumbnailLarge = R.drawable.way_home_thumb_large,
            credit = "Music powered by BreakingCopyright"
        ),
        MusicTrack(
            id = "5",
            song = "Indian Fusion",
            composer = "Shahed",
            songFile = R.raw.indian_fusion,
            thumbnail = R.mipmap.indian_fusion_thumb,
            thumbnailLarge = R.drawable.indian_fusion_thumb_large,
            credit = "Music powered by BreakingCopyright"
        ),
        MusicTrack(
            id = "6",
            song = "The Epic Hero",
            composer = "Keys Of Moon",
            songFile = R.raw.epic_hero,
            thumbnail = R.mipmap.epic_hero_thumb,
            thumbnailLarge = R.drawable.epic_hero_thumb_large,
            credit = "Music powered by BreakingCopyright"
        )
    )
}