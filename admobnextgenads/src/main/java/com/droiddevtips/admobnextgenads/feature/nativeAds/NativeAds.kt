@file:OptIn(ExperimentalPagerApi::class)

package com.droiddevtips.admobnextgenads.feature.nativeAds

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ScrollableTabRow
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Tab
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.droiddevtips.admobnextgenads.core.data.TabItem
import com.droiddevtips.admobnextgenads.core.data.nativeAdFullscreenRoute
import com.droiddevtips.admobnextgenads.core.data.nativeAdNonVideoRoute
import com.droiddevtips.admobnextgenads.core.data.nativeAdVideoRoute
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

/**
 * This is the native ad composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun NativeAds(modifier: Modifier = Modifier) {

    val tabs = listOf(
        TabItem.NativeAdVideo,
        TabItem.NativeAdNonVideo,
        TabItem.NativeAdFullscreen
    )

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
                title = {
                    Text(
                        "Native Ad",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                })
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {

            ScrollableTabRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                selectedTabIndex = selectedIndex,
                backgroundColor = MaterialTheme.colorScheme.background,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier.pagerTabIndicatorOffset(
                            pagerState = pagerState,
                            tabPositions = tabPositions
                        ),
                        height = 3.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                tabs = {
                    tabs.forEachIndexed { index, tab ->
                        val selected = selectedIndex == index
                        Tab(selected = selected, text = {
                            Text(
                                text = stringResource(id = tab.title),
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                color = if (selected) MaterialTheme.colorScheme.primary else Color.LightGray
                            )
                        }, onClick = {
                            selectedIndex = index
                            scope.launch {
                                pagerState.animateScrollToPage(selectedIndex)
                            }
                        })
                    }
                }
            )

            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize(),
                state = pagerState,
                userScrollEnabled = false,
                count = tabs.size
            ) { pageIndex ->

                when (tabs[pageIndex].route) {
                    nativeAdVideoRoute -> NativeAdVideo()
                    nativeAdNonVideoRoute -> NativeAdNonVideo()
                    nativeAdFullscreenRoute -> NativeAdFullscreen()
                }
            }
        }
    }
}