package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data

import com.droiddevtips.multiscreensupport.R

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed class TabItem(val titleRes: Int, val icon: Int) {

    data object Home: TabItem(titleRes = R.string.home, R.drawable.home_icon)
    data object News: TabItem(titleRes = R.string.news, R.drawable.news_icon)
    data object Follower: TabItem(titleRes = R.string.follower, R.drawable.follower_icon)

}