package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.listDetailItem.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class ListDetailViewState(
    val itemsList: List<ListDetailItem> = emptyList(),
    val selectedItem:ListDetailItem? = null,
    val showLoadingView: Boolean = true,
    val visibleIndex: Int = 0
): Parcelable