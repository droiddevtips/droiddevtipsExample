package com.droiddevtips.floatingtabbarandpip.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.DeviceOrientation
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.floatingtabbarandpip.common.videoList.ui.MainVideoListView
import com.droiddevtips.floatingtabbarandpip.core.videosRepository
import com.droiddevtips.floatingtabbarandpip.feature.favorites.FavoriteViewModel
import com.droiddevtips.floatingtabbarandpip.feature.favorites.FavoriteViewModelFactory
import com.droiddevtips.floatingtabbarandpip.feature.floatingTabBar.ui.FloatingToolBar
import com.droiddevtips.floatingtabbarandpip.feature.profile.ProfileView
import com.droiddevtips.floatingtabbarandpip.feature.videos.ui.VideosViewModel
import com.droiddevtips.floatingtabbarandpip.feature.videos.ui.VideosViewModelFactory
import kotlinx.coroutines.launch

/**
 * The main composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun MainView(modifier: Modifier = Modifier) {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 3 })
    val selectedTabIndex = rememberSaveable { mutableStateOf(0) }
    val windowSize = deviceDetectorCurrentWindowSize()

    Box(
        modifier = modifier
    ) {

        MainContentView(
            pagerState = pagerState,
            modifier = Modifier.fillMaxSize(),
            onTabSelected = { pageIndex ->
                selectedTabIndex.value = pageIndex
                scope.launch {
                    pagerState.animateScrollToPage(pageIndex)
                }
            }
        )

        MainViewFloatingToolBar(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier
                .navigationBarsPadding()
                .then(
                    if (windowSize.device == Device.Tablet && windowSize.orientation == DeviceOrientation.Landscape) {
                        Modifier.align(alignment = Alignment.CenterStart)
                    } else {
                        Modifier.align(alignment = Alignment.BottomCenter)
                    }
                ),
            onTabSelected = { tabIndex ->
                selectedTabIndex.value = tabIndex
                scope.launch {
                    pagerState.animateScrollToPage(tabIndex)
                }
            }
        )
    }
}

@Composable
private fun MainContentView(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier.background(color = MaterialTheme.colorScheme.background)
    ) { page ->

        when (page) {

            0 -> {

                val videoListViewModel: VideosViewModel = viewModel(
                    factory = VideosViewModelFactory(
                        repository = videosRepository
                    )
                )
                MainVideoListView(videoListViewModel = videoListViewModel, modifier = Modifier.fillMaxSize())
            }

            1 -> {

                val favoriteViewModel: FavoriteViewModel = viewModel(
                    factory = FavoriteViewModelFactory(
                        repository = videosRepository
                    )
                )
                MainVideoListView(videoListViewModel = favoriteViewModel, modifier = Modifier.fillMaxSize())
            }

            2 -> ProfileView()

            else -> Unit
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { pageIndex ->
            onTabSelected(pageIndex)
        }
    }
}

@Composable
private fun MainViewFloatingToolBar(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit
) {
    FloatingToolBar(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        onTabSelected = onTabSelected
    )
}