@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.droiddevtips.musicplayer.mainView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun MainMusicPlayerView(modifier: Modifier = Modifier) {

    val musicPlayerViewModel: MusicPlayerViewModel =
        viewModel(factory = MusicPlayerViewModelFactory())
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
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, musicTrack)
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {

                val selectedMusicTrack = navigator.currentDestination?.contentKey
                if (selectedMusicTrack != null) {
                    MusicPlayerView(musicTrack = selectedMusicTrack, modifier = Modifier.fillMaxSize())
                } else {
                    Box(modifier = Modifier.fillMaxSize().background(color = Color.Blue))
                }

                /*
                navigator.currentDestination?.contentKey?.let { musicTrack ->
                    MusicPlayerView(musicTrack = musicTrack, modifier = Modifier.fillMaxSize())
                    return@let
                }
                Box(modifier = Modifier.fillMaxSize().background(color = Color.Blue))
                */
            }
        }
    )
}

@Composable
private fun TrackListView(
    viewState: MusicPlayerViewState,
    modifier: Modifier = Modifier,
    onMusicTrackSelected: (MusicTrack) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(viewState.musicList) { musicTrack ->
            MusicTrackListItem(
                musicTrack = musicTrack,
                modifier = Modifier.fillMaxWidth(),
                onMusicTrackSelected = onMusicTrackSelected
            )
        }
    }
}