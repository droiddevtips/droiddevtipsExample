package com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.droiddevtips.admobnextgenads.feature.inlineAdaptive.data.InlineAdaptiveTab
import com.droiddevtips.admobnextgenads.feature.inlineAdaptive.data.listRoute
import com.droiddevtips.admobnextgenads.feature.inlineAdaptive.data.staticRoute
import com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui.listView.ui.InlineAdaptiveListView
import com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui.listView.ui.InlineAdaptiveViewModel
import com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui.staticView.InlineAdaptiveStatic
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun InlineAdaptive(modifier: Modifier = Modifier) {

    val tabs = listOf(
        InlineAdaptiveTab.Static,
        InlineAdaptiveTab.List
    )

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TabRow(
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
        }
    ) { innerPadding ->

        HorizontalPager(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding()
                )
                .fillMaxSize(),
            state = pagerState,
            count = tabs.size
        ) { pageIndex ->

            when (tabs[pageIndex].route) {

                staticRoute -> BannerAdStaticView()

                listRoute -> BannerAdListView()
            }
        }
    }
}

@Composable
private fun BannerAdStaticView(modifier: Modifier = Modifier) {
    InlineAdaptiveStatic(modifier = modifier)
}

@Composable
private fun BannerAdListView() {
    val listViewModel = remember {
        InlineAdaptiveViewModel()
    }
    val viewState = listViewModel.inlineAdaptiveViewState.collectAsStateWithLifecycle()

    InlineAdaptiveListView(
        modifier = Modifier.fillMaxSize(),
        viewState = viewState
    )
}