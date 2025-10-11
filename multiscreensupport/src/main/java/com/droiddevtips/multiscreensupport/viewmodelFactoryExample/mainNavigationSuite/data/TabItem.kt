package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.data

import com.droiddevtips.multiscreensupport.R
import com.droiddevtips.multiscreensupport.common.data.AppString
import com.droiddevtips.multiscreensupport.common.data.Drawable

/**
 * The tab items sealed class
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed class TabItem(val titleRes: Int, val icon: Int, val route: String) {

    data object Home: TabItem(titleRes = AppString.home, Drawable.home_icon, route = MainNestedNavRoute.MainNestedNav.route)
    data object News: TabItem(titleRes = AppString.news, Drawable.news_icon, route = MainNestedNavRoute.News.route)
    data object Follower: TabItem(titleRes = AppString.follower, Drawable.follower_icon, route = MainNestedNavRoute.Follower.route)

}