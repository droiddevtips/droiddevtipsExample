@file:OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)

package com.droiddevtips.musicplayer.ui.mainView.ui

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.DeviceOrientation
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.musicplayer.R
import com.droiddevtips.musicplayer.ui.MusicPlayerViewModel
import com.droiddevtips.musicplayer.ui.MusicPlayerViewModelFactory
import com.droiddevtips.musicplayer.ui.mainView.data.MusicPlayerAction
import com.droiddevtips.musicplayer.ui.mainView.data.MusicPlayerViewState
import com.droiddevtips.musicplayer.ui.mainView.data.MusicTrack
import com.droiddevtips.musicplayer.ui.mediaListIem.MusicTrackListItem
import com.droiddevtips.musicplayer.ui.miniplayer.MiniPlayerContainer
import com.droiddevtips.musicplayer.ui.musicPlayerView.MusicPlayerView
import kotlinx.coroutines.launch

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun MainMusicPlayerView(
    application: Application,
    modifier: Modifier = Modifier
) {
    val musicPlayerViewModel: MusicPlayerViewModel =
        viewModel(factory = MusicPlayerViewModelFactory(application = application))
    val musicPlayerViewState =
        musicPlayerViewModel.musicPlayerViewState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val navigator = rememberListDetailPaneScaffoldNavigator<MusicTrack>()
    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            AnimatedPane {
                TrackListView(
                    viewState = musicPlayerViewState.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    onMusicTrackSelected = { musicTrack ->
                        scope.launch {
                            musicPlayerViewModel.performAction(
                                MusicPlayerAction.ChangeMusicTrack(
                                    track = musicTrack
                                )
                            )
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, musicTrack)
                        }
                    },
                    onViewAction = musicPlayerViewModel::performAction
                )
            }
        },
        detailPane = {
            AnimatedPane {
                if (musicPlayerViewState.value.currentlyPlaying != null) {
                    MusicPlayerView(
                        viewState = musicPlayerViewState.value,
                        modifier = Modifier.fillMaxSize(),
                        action = musicPlayerViewModel::performAction
                    )
                } else {
                    MusicPlayerPlaceHolder(modifier = Modifier.fillMaxSize())
                }
            }
        }
    )
}

@Composable
private fun TrackListView(
    viewState: MusicPlayerViewState,
    modifier: Modifier = Modifier,
    onViewAction: (MusicPlayerAction) -> Unit,
    onMusicTrackSelected: (MusicTrack) -> Unit
) {

    val windowSize = deviceDetectorCurrentWindowSize()

    val colors = if (isSystemInDarkTheme()) listOf(
        Color.DarkGray,
        Color.DarkGray,
        MaterialTheme.colorScheme.background
    ) else listOf(
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.primary.copy(alpha = 0.20f),
        MaterialTheme.colorScheme.background
    )

    Box {
        LazyColumn(
            modifier = modifier.background(
                brush = Brush.linearGradient(
                    colors = colors,
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(viewState.musicList) { musicTrack ->
                MusicTrackListItem(
                    musicTrack = musicTrack,
                    currentlyPlaying = viewState.currentlyPlaying?.id ?: "",
                    showAudioVisualizer = viewState.showAudioVisualizer,
                    modifier = Modifier.fillMaxWidth(),
                    onMusicTrackSelected = onMusicTrackSelected
                )
            }
        }

        if (windowSize.orientation == DeviceOrientation.Portrait && windowSize.device == Device.Mobile && viewState.showMiniPlayer) {
            MiniPlayerContainer(
                modifier = Modifier.align(alignment = Alignment.BottomCenter),
                viewState = viewState,
                onViewAction = onViewAction
            )
        }
    }
}

@Preview
@Composable
private fun MusicPlayerPlaceHolder(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier.align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.no_music_icon),
                modifier = Modifier
                    .size(100.dp),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
            )

            Text(text = stringResource(id = R.string.placeholder_text))
        }
    }
}

