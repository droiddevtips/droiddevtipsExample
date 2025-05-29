package com.droiddevtips.admobnextgenads.feature.inlineAdaptive.data

import com.droiddevtips.admobnextgenads.core.data.AppString

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

val staticRoute = "inline_static"
val listRoute = "inline_list"
val collapsibleRoute = "collapsible_list"
val anchoredRoute = "anchored_list"

sealed class InlineAdaptiveTab(val route: String, val title: Int) {
    data object Static : InlineAdaptiveTab(route = staticRoute, title = AppString.inline_static)
    data object Inline : InlineAdaptiveTab(route = listRoute, title = AppString.inline_list)
    data object Collapsible : InlineAdaptiveTab(route = collapsibleRoute, title = AppString.collapsible)
    data object Anchored : InlineAdaptiveTab(route = anchoredRoute, title = AppString.anchored)
}