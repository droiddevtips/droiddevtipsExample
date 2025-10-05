package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.data

import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.article.data.Article

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface HomeViewEvent {

    data class ToggleLoadingView(val visible: Boolean) : HomeViewEvent
    data class NavigateToDetail(val article: Article) : HomeViewEvent
    data class SetScrollPosition(val index: Int) : HomeViewEvent

}