package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Main nested nav route
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
sealed class MainNestedNavRoute(val route:String): Parcelable {

    data object MainNestedNav: MainNestedNavRoute(route = "home")
    data object News: MainNestedNavRoute(route = "news")
    data object Follower: MainNestedNavRoute(route = "follower")
    data object ArticleDetail: MainNestedNavRoute(route = "article_detail")

}