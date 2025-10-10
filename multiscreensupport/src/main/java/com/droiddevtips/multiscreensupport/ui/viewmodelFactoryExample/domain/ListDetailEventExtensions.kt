package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.domain

import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailEvent
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailItem

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

fun ListDetailEvent.parseEvent(
    updateViewModel: (ListDetailEvent) -> Unit,
    navigate: (ListDetailItem) -> Unit
) {
    when(this) {

        is ListDetailEvent.NavigateToDetail -> {
            updateViewModel(this)
            navigate(this.item)
        }

        else -> updateViewModel(this)
    }
}