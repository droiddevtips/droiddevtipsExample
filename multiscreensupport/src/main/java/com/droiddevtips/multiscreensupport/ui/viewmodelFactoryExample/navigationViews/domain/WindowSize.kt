package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.domain

import android.content.res.Configuration
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.window.core.layout.WindowSizeClass.Companion.HEIGHT_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.HEIGHT_DP_MEDIUM_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun currentWindowSize(): AppWindowSizeAndOrientation {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    val configuration = LocalConfiguration.current
    val widthGreaterThanMedium =
        windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)
    val widthGreaterThanExpanded =
        windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND)
    val heightGreaterThanMedium =
        windowSizeClass.isHeightAtLeastBreakpoint(HEIGHT_DP_MEDIUM_LOWER_BOUND)

    val width = when  {
        windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND) -> AppWindowSize.Expanded
        windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND) -> AppWindowSize.Medium
        else -> AppWindowSize.Compact
    }

    val height = when {
        windowSizeClass.isHeightAtLeastBreakpoint(HEIGHT_DP_EXPANDED_LOWER_BOUND) -> AppWindowSize.Expanded
        windowSizeClass.isHeightAtLeastBreakpoint(HEIGHT_DP_MEDIUM_LOWER_BOUND) -> AppWindowSize.Medium
        else -> AppWindowSize.Compact
    }

    val orientation = when(configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> DeviceOrientation.Landscape
        Configuration.ORIENTATION_PORTRAIT -> DeviceOrientation.Portrait
        else -> DeviceOrientation.Undefined
    }

    val device: Device = when {
        (widthGreaterThanExpanded && heightGreaterThanMedium) || (widthGreaterThanMedium && heightGreaterThanMedium) -> Device.Tablet
        else -> Device.Mobile
    }

    return AppWindowSizeAndOrientation(device = device, orientation = orientation, windowWidthSize = width, windowHeightSize = height)
}

data class AppWindowSizeAndOrientation(val device: Device, val orientation: DeviceOrientation, val windowWidthSize: AppWindowSize, val windowHeightSize: AppWindowSize)

sealed class Device {
    object Mobile: Device()
    object Tablet: Device()
}

sealed class DeviceOrientation {
    object Portrait: DeviceOrientation()
    object Landscape: DeviceOrientation()
    object Undefined: DeviceOrientation()
}

sealed class AppWindowSize {
    data object Compact: AppWindowSize()
    data object Medium: AppWindowSize()
    data object Expanded: AppWindowSize()
}
