package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.listDetailItem.data

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed interface ListDetailEvent {
    data class ToggleLoadingView(val visible: Boolean) : ListDetailEvent
    data class NavigateToDetail(val item: ListDetailItem) : ListDetailEvent
    data class SetScrollPosition(val index: Int) : ListDetailEvent
}