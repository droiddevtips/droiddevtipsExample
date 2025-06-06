package com.droiddevtips.admobnextgenads.core.data

/**
 * The app tab items
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

val staticRoute = "inline_static"
val listRoute = "inline_list"
val collapsibleTopRoute = "collapsible_top"
val collapsibleBottomRoute = "collapsible_bottom"
val anchoredRoute = "anchored_list"

val nativeAdVideoRoute = "native_ad_video"
val nativeAdNonVideoRoute = "native_ad_non_video"
val nativeAdFullscreenRoute = "native_ad_fullscreen"

sealed class TabItem(val route: String, val title: Int) {
    data object Static : TabItem(route = staticRoute, title = AppString.inline_static)
    data object InlineItem : TabItem(route = listRoute, title = AppString.inline_list)
    data object CollapsibleTop : TabItem(route = collapsibleTopRoute, title = AppString.collapsible_top)
    data object CollapsibleBottom : TabItem(route = collapsibleBottomRoute, title = AppString.collapsible_bottom)
    data object Anchored : TabItem(route = anchoredRoute, title = AppString.anchored)

    data object NativeAdVideo : TabItem(route = nativeAdVideoRoute, title = AppString.native_ad_video)
    data object NativeAdNonVideo : TabItem(route = nativeAdNonVideoRoute, title = AppString.native_ad_non_video)
    data object NativeAdFullscreen : TabItem(route = nativeAdFullscreenRoute, title = AppString.native_ad_fullscreen)
}