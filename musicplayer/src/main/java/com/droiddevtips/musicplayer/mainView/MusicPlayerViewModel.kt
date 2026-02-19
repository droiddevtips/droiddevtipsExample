package com.droiddevtips.musicplayer.mainView

import android.app.Application
import android.content.ComponentName
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Metadata
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.Player.MEDIA_ITEM_TRANSITION_REASON_AUTO
import androidx.media3.common.Player.MEDIA_ITEM_TRANSITION_REASON_PLAYLIST_CHANGED
import androidx.media3.common.Player.MEDIA_ITEM_TRANSITION_REASON_REPEAT
import androidx.media3.common.Player.MEDIA_ITEM_TRANSITION_REASON_SEEK
import androidx.media3.common.Player.STATE_BUFFERING
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.Player.STATE_IDLE
import androidx.media3.common.Player.STATE_READY
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.droiddevtips.musicplayer.MusicPlayerService
import com.droiddevtips.musicplayer.R
import com.droiddevtips.musicplayer.extensions.asMediaItemList
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
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MusicPlayerViewState())

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


                        Log.i(
                            "TAG35",
                            "Current: $currentPositionText - total duration: $totalDurationString - percentage: ${percentage}"
                        )

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

    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
        super.onMediaMetadataChanged(mediaMetadata)
        Log.i(
            "TAG23",
            "Media meta data changed: ${mediaMetadata.composer}\n media type: ${mediaMetadata.mediaType}\n Artwork Uri: ${mediaMetadata.artworkUri}\nArtist: ${mediaMetadata.artist}\nTitle: ${mediaMetadata.title}\nSubtitle: ${mediaMetadata.subtitle}\nPosition: ${mediaController?.currentPosition}\nDuration: ${mediaController?.duration}"
        )
    }

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        super.onPlayWhenReadyChanged(playWhenReady, reason)
        Log.i("TAG23", "On play when ready changed: $playWhenReady -> reason: $reason")
    }

    override fun onIsLoadingChanged(isLoading: Boolean) {
        super.onIsLoadingChanged(isLoading)
        Log.i("TAG23", "On is loading changed -> $isLoading \n\n")
    }

    override fun onPlaylistMetadataChanged(mediaMetadata: MediaMetadata) {
        super.onPlaylistMetadataChanged(mediaMetadata)
        Log.i("TAG23", "On play list meta data changed -> $mediaMetadata \n\n")
    }

    override fun onPositionDiscontinuity(
        oldPosition: Player.PositionInfo,
        newPosition: Player.PositionInfo,
        reason: Int
    ) {
        super.onPositionDiscontinuity(oldPosition, newPosition, reason)
        Log.i(
            "TAG23",
            "On position discontinuity -> old: ${oldPosition.positionMs}, new: ${newPosition.positionMs}, reason: ${reason} \n\n"
        )
    }


    override fun onRepeatModeChanged(repeatMode: Int) {
        super.onRepeatModeChanged(repeatMode)
        Log.i("TAG23", "On repeat mode changed -> $repeatMode \n\n")
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        super.onShuffleModeEnabledChanged(shuffleModeEnabled)
        Log.i("TAG23", "On shuffle mode enable changed -> $shuffleModeEnabled \n\n")
    }

    override fun onTimelineChanged(timeline: Timeline, reason: Int) {
        super.onTimelineChanged(timeline, reason)
        Log.i(
            "TAG23",
            "On timeline changed -> period ${timeline.periodCount}, window: ${timeline.windowCount} \n\n"
        )

        if (reason == Player.TIMELINE_CHANGE_REASON_SOURCE_UPDATE) {
            Log.i(
                "TAG23",
                "Position: ${mediaController?.currentPosition} - duration: ${mediaController?.duration}"
            )
        }

    }

    override fun onAvailableCommandsChanged(availableCommands: Player.Commands) {
        super.onAvailableCommandsChanged(availableCommands)
        Log.i("TAG23", "On available command changed -> $availableCommands \n\n")

    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
        super.onPlaybackParametersChanged(playbackParameters)
        Log.i("TAG23", "On playback parameters changed -> parameters: ${playbackParameters} \n\n")
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        Log.i("TAG23", "Is playing: $isPlaying \n\n")

        _musicPlayerViewState.update { state ->
            state.copy(
                showPauseButton = isPlaying,
                showPlayButton = !isPlaying,
                showMiniPlayer = if (_musicPlayerViewState.value.showMiniPlayer) true else isPlaying,
                enablePreviousButton = mediaController?.hasPreviousMediaItem() ?: false,
                enableNextButton = mediaController?.hasNextMediaItem() ?: false
            )
        }
    }

    @UnstableApi
    override fun onMetadata(metadata: Metadata) {
        super.onMetadata(metadata)
        Log.i("TAG23", "On meta data: $metadata \n\n")
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        Log.i(
            "TAG23", "On media item transition: $mediaItem, reason: ${
                when (reason) {
                    MEDIA_ITEM_TRANSITION_REASON_REPEAT -> "Reason repeat"
                    MEDIA_ITEM_TRANSITION_REASON_AUTO -> "Reason auto"
                    MEDIA_ITEM_TRANSITION_REASON_SEEK -> "Reason seek"
                    MEDIA_ITEM_TRANSITION_REASON_PLAYLIST_CHANGED -> "Reason playlist changed"
                    else -> "Unknown"
                }
            } \n\n"
        )

        val newItem = musicList.asSequence().filter { it.id == mediaItem?.mediaId }.firstOrNull()
        Log.i("TAG23", "new item: $newItem")
        newItem?.let {
            _musicPlayerViewState.update { it.copy(currentlyPlaying = newItem) }
        }


    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        Log.i("TAG23", "Playback state: $playbackState \n\n")
        when (playbackState) {

            STATE_IDLE -> {
                Log.i("TAG23", "Playback state -> STATE_IDLE")
            }

            STATE_BUFFERING -> {
                Log.i("TAG23", "Playback state -> STATE_BUFFERING")
            }

            STATE_READY -> {
                Log.i("TAG23", "Playback state -> STATE_READY")
            }

            STATE_ENDED -> {
                Log.i("TAG23", "Playback state -> STATE_ENDED")
            }
        }
    }

    fun performAction(action: MusicPlayerAction) {

        Log.i("TAG23","Perform action -> $action")

        when (action) {
            is MusicPlayerAction.ChangeMusicTrack -> {

                val index = musicList.indexOf(action.track)
                Log.i("TAG23", "Track index: $index")

                _musicPlayerViewState.update { it.copy(currentlyPlaying = action.track) }
                mediaController?.apply {
                    seekTo(index, 0)
                    play()
                }
            }

            is MusicPlayerAction.Pause -> {
                mediaController?.apply {
                    if (isPlaying) {
                        pause()
                        _musicPlayerViewState.update { state ->
                            state.copy(
                                showPauseButton = false,
                                showPlayButton = true
                            )
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
                                showPauseButton = true,
                                showPlayButton = false,
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
                                .firstOrNull()?.let { currentTrack ->

                                    val index = musicList.indexOf(currentTrack)
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
                                .firstOrNull()?.let { currentTrack ->

                                    val index = musicList.indexOf(currentTrack)
                                    if (index != -1) {
                                        mediaController?.seekTo(index - 1, 0)
                                    }
                                }
                        }
                    }
                }
            }

            MusicPlayerAction.CloseMiniPlayer -> {
                mediaController?.apply {
                    if (isPlaying) {
                        stop()
                    }
                }
                _musicPlayerViewState.update { it.copy(showMiniPlayer = false, currentlyPlaying = null) }
            }

            is MusicPlayerAction.ToggleMiniPlayerView -> {
                _musicPlayerViewState.update { it.copy(expandMiniPlayer = !_musicPlayerViewState.value.expandMiniPlayer) }
            }
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