package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
sealed class Routes(val route:String): Parcelable {

    data object Home: Routes(route = "home")
    data object News: Routes(route = "news")
    data object Follower: Routes(route = "follower")
    data object ArticleDetail: Routes(route = "article_detail")

}