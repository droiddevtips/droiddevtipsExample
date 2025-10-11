package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The list detail item data model.
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class ListDetailItem(
    val title: String,
    val description: String,
    val image: Int,
    val viewType: ViewType
) : Parcelable
