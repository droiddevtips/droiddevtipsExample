package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.mainNavigationSuite.ui.home.domain

import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.article.data.Article
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.mainNavigationSuite.ui.home.data.HomeViewEvent

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

fun HomeViewEvent.parseEvent(
    updateViewModel: (HomeViewEvent) -> Unit,
    navigate: (Article) -> Unit
) {
    when(this) {

        is HomeViewEvent.NavigateToDetail -> {
            updateViewModel(this)
            navigate(this.article)
        }

        else -> updateViewModel(this)
    }
}