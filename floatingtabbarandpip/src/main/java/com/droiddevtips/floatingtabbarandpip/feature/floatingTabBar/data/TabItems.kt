package com.droiddevtips.floatingtabbarandpip.feature.floatingTabBar.data

import com.droiddevtips.floatingtabbarandpip.R

/**
 * The tab item tabs items
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
data class TabItem(val tabIcon: Int, val title: Int)

val items = listOf(
    TabItem(tabIcon = R.drawable.video_icon, title = R.string.videos),
    TabItem(tabIcon = R.drawable.favorite_icon, title = R.string.favorite),
    TabItem(tabIcon = R.drawable.profile_icon, title = R.string.profile),
)