package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The view type sealed interface.
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
sealed interface ViewType: Parcelable {
    data object Home: ViewType
    data object News: ViewType
    data object Follower: ViewType
}