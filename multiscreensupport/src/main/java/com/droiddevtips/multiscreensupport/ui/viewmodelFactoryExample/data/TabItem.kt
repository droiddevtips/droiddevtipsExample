package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data

import com.droiddevtips.multiscreensupport.R

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed class TabItem(val titleRes: Int, val icon: Int, val route: String) {

    data object Home: TabItem(titleRes = R.string.home, R.drawable.home_icon, route = "home")
    data object News: TabItem(titleRes = R.string.news, R.drawable.news_icon, route = "news")
    data object Follower: TabItem(titleRes = R.string.follower, R.drawable.follower_icon, route = "follower")

}