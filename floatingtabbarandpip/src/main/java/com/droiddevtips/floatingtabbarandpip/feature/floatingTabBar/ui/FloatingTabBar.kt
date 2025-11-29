@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.droiddevtips.floatingtabbarandpip.feature.floatingTabBar.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalFloatingToolbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.DeviceOrientation
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.floatingtabbarandpip.feature.floatingTabBar.data.items

/**
 * The floating tab composable
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun FloatingTabBar(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit
) {

    val windowSize = deviceDetectorCurrentWindowSize()

    if (windowSize.device == Device.Tablet && windowSize.orientation == DeviceOrientation.Landscape) {
        VerticalTabBar(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier,
            onTabSelected = onTabSelected
        )
    } else {
        HorizontalTabBar(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier,
            onTabSelected = onTabSelected
        )
    }
}

@Composable
fun HorizontalTabBar(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit
) {

    HorizontalFloatingToolbar(
        expanded = false,
        colors = FloatingToolbarDefaults.standardFloatingToolbarColors()
            .copy(toolbarContainerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = modifier
            .padding(bottom = 16.dp)
    ) {

        items.forEachIndexed { index, tabIcon ->
            IconButton(onClick = {
                onTabSelected(index)
            }) {
                Image(
                    painter = painterResource(id = tabIcon.tabIcon),
                    modifier = Modifier.size(24.dp),
                    alpha = if (isSystemInDarkTheme() && selectedTabIndex != index) {
                        0.35f
                    } else {
                        1.0f
                    },
                    colorFilter = if (!isSystemInDarkTheme() && selectedTabIndex == index) {
                        ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
                    } else {
                        null
                    },
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun VerticalTabBar(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit
) {

    VerticalFloatingToolbar(
        expanded = false, colors = FloatingToolbarDefaults.standardFloatingToolbarColors()
            .copy(toolbarContainerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = modifier.padding(start = 16.dp)
    ) {

        items.forEachIndexed { index, tabItem ->
            IconButton(onClick = {
                onTabSelected(index)
            }) {
                Image(
                    painter = painterResource(id = tabItem.tabIcon),
                    modifier = Modifier.size(24.dp),
                    alpha = if (isSystemInDarkTheme() && selectedTabIndex != index) {
                        0.35f
                    } else {
                        1.0f
                    },
                    colorFilter = if (!isSystemInDarkTheme() && selectedTabIndex == index) {
                        ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
                    } else {
                        null
                    },
                    contentDescription = null
                )
            }
        }
    }
}