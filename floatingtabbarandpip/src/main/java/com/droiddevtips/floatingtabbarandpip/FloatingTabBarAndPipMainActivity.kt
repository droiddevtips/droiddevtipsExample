@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.droiddevtips.floatingtabbarandpip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.DeviceOrientation
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.floatingtabbarandpip.favorites.FavoriteView
import com.droiddevtips.floatingtabbarandpip.profile.ProfileView
import com.droiddevtips.floatingtabbarandpip.videos.VideosView
import com.droiddevtips.typography.DroidDevTipsTheme
import kotlinx.coroutines.launch

class FloatingTabBarAndPipMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val scope = rememberCoroutineScope()
                    val pagerState = rememberPagerState(pageCount = { 3 })
                    val selectedTabIndex = rememberSaveable { mutableStateOf(0) }
                    val windowSize = deviceDetectorCurrentWindowSize()

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {

                        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->

                            val item = items[page]

                            when(page) {
                                0 -> VideosView()
                                1 -> FavoriteView()
                                2 -> ProfileView()
                                else -> Unit
                            }
                        }

                        FloatingTabBar(selectedTabIndex = selectedTabIndex.value, modifier = Modifier.then(
                            if (windowSize.device == Device.Tablet && windowSize.orientation == DeviceOrientation.Landscape) {
                                Modifier.align(alignment = Alignment.CenterStart)
                            } else {
                                Modifier.align(alignment = Alignment.BottomCenter)
                            }
                        ), onTabSelected = { tabIndex ->
                            selectedTabIndex.value = tabIndex
                            scope.launch {
                                pagerState.animateScrollToPage(tabIndex)
                            }
                        })
                    }

                    LaunchedEffect(pagerState) {
                        snapshotFlow { pagerState.currentPage }.collect { pageIndex ->
                            selectedTabIndex.value = pageIndex
                        }
                    }
                }
            }
        }
    }
}