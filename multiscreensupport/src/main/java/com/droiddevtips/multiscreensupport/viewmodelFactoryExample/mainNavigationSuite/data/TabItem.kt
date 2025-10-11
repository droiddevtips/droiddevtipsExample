package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.data

import com.droiddevtips.multiscreensupport.R

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed class TabItem(val titleRes: Int, val icon: Int, val route: String) {

    data object Home: TabItem(titleRes = R.string.home, R.drawable.home_icon, route = MainNestedNavRoute.MainNestedNav.route)
    data object News: TabItem(titleRes = R.string.news, R.drawable.news_icon, route = MainNestedNavRoute.News.route)
    data object Follower: TabItem(titleRes = R.string.follower, R.drawable.follower_icon, route = MainNestedNavRoute.Follower.route)

}