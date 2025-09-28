package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.droiddevtips.multiscreensupport.R
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.MultiScreenSupportAction
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.TabItem
import kotlinx.coroutines.launch

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun ViewModelFactoryExample(modifier: Modifier = Modifier) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val multiScreenViewModel: MultiScreenViewModel = viewModel(
        factory =
            MultiScreenViewModelFactory()
    )

    val counter = multiScreenViewModel.counter.collectAsStateWithLifecycle()

    val width = when (windowSizeClass.windowWidthSizeClass) {

        WindowWidthSizeClass.COMPACT -> WindowWidthSizeClass.COMPACT.toString()
        WindowWidthSizeClass.MEDIUM -> WindowWidthSizeClass.MEDIUM.toString()
        WindowWidthSizeClass.EXPANDED -> WindowWidthSizeClass.EXPANDED.toString()
        else -> "Unknown width"
    }

    val height = when (windowSizeClass.windowHeightSizeClass) {

        WindowHeightSizeClass.COMPACT -> WindowHeightSizeClass.COMPACT.toString()
        WindowHeightSizeClass.MEDIUM -> WindowHeightSizeClass.MEDIUM.toString()
        WindowHeightSizeClass.EXPANDED -> WindowHeightSizeClass.EXPANDED.toString()
        else -> "Unknown height"
    }


    val scope = rememberCoroutineScope()
    var selectedDestination by rememberSaveable { mutableIntStateOf(0) }

    val tabs = listOf(
        TabItem.Home,
        TabItem.News,
        TabItem.Follower
    )
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    Scaffold(modifier = modifier, bottomBar = {
        PrimaryTabRow(
            selectedTabIndex = selectedDestination,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, tabItem ->

                Tab(
                    selected = selectedDestination == index,
                    onClick = {
                        scope.launch {
                            selectedDestination = index
                            pagerState.animateScrollToPage(page = index)
                        }
                    }, text = {
                        TabTitle(selected = index == selectedDestination, text = tabItem.titleRes)
                    }, icon = {
                        TabIcon(
                            selected = index == selectedDestination,
                            icon = tabItem.icon
                        )
                    }
                )
            }
        }
    }) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
        ) {

            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                state = pagerState
            ) { page ->

                Text("Page: $page")

            }
        }
    }
}

@Composable
private fun TabIcon(
    modifier: Modifier = Modifier,
    selected: Boolean,
    icon: Int,
    contentDescription: String? = null
) {
    Icon(
        modifier = modifier.size(24.dp),
        painter = painterResource(icon),
        contentDescription = contentDescription,
        tint = if (selected) MaterialTheme.colorScheme.primary else if (isSystemInDarkTheme()) Color.White else Color.Black
    )
}

@Composable
private fun TabTitle(
    modifier: Modifier = Modifier,
    selected: Boolean,
    text: Int
) {
    Text(
        modifier = modifier,
        text = stringResource(id = text),
        color = if (selected) MaterialTheme.colorScheme.primary else if (isSystemInDarkTheme()) Color.White else Color.Black
    )
}