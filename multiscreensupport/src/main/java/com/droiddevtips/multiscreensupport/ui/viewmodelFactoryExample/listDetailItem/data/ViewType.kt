package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.listDetailItem.data

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed interface ViewType {
    data object Home: ViewType
    data object News: ViewType
    data object Follower: ViewType
}