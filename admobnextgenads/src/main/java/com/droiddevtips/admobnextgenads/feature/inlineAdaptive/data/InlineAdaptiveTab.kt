package com.droiddevtips.admobnextgenads.feature.inlineAdaptive.data

import com.droiddevtips.admobnextgenads.core.data.AppString

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

val staticRoute = "inline_static"
val listRoute = "inline_list"
val collapsibleTopRoute = "collapsible_top"
val collapsibleBottomRoute = "collapsible_bottom"
val anchoredRoute = "anchored_list"

sealed class InlineAdaptiveTab(val route: String, val title: Int) {
    data object Static : InlineAdaptiveTab(route = staticRoute, title = AppString.inline_static)
    data object Inline : InlineAdaptiveTab(route = listRoute, title = AppString.inline_list)
    data object CollapsibleTop : InlineAdaptiveTab(route = collapsibleTopRoute, title = AppString.collapsible_top)
    data object CollapsibleBottom : InlineAdaptiveTab(route = collapsibleBottomRoute, title = AppString.collapsible_bottom)
    data object Anchored : InlineAdaptiveTab(route = anchoredRoute, title = AppString.anchored)
}