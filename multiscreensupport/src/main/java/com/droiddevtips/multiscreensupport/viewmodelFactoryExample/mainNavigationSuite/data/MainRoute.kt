package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Main routes of the main page
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
sealed class MainRoute(val route:String): Parcelable {
    data object Main: MainRoute(route = "main")
    data object Detail: MainRoute(route = "detail")
}