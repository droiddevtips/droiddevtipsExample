package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.data

import android.os.Parcelable
import com.droiddevtips.multiscreensupport.R
import kotlinx.parcelize.Parcelize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed class TabItem(val titleRes: Int, val icon: Int, val route: String) {

    data object Home: TabItem(titleRes = R.string.home, R.drawable.home_icon, route = Routes.Home.route)
    data object News: TabItem(titleRes = R.string.news, R.drawable.news_icon, route = Routes.News.route)
    data object Follower: TabItem(titleRes = R.string.follower, R.drawable.follower_icon, route = Routes.Follower.route)

}