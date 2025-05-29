package com.droiddevtips.admobnextgenads.feature.inlineAdaptive.data

import com.droiddevtips.admobnextgenads.core.data.AppString

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

val staticRoute = "inline_static"
val listRoute = "inline_list"

sealed class InlineAdaptiveTab(val route: String, val title: Int) {
    data object Static : InlineAdaptiveTab(route = staticRoute, title = AppString.inline_static)
    data object List : InlineAdaptiveTab(route = listRoute, title = AppString.inline_list)
}