package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The list detail view state data model.
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class ListDetailViewState(
    val itemsList: List<ListDetailItem> = emptyList(),
    val selectedItem:ListDetailItem? = null,
    val showEmptyView: Boolean = false,
    val showLoadingView: Boolean = true,
    val visibleIndex: Int = 0,
    val viewType: ViewType = ViewType.Home
): Parcelable