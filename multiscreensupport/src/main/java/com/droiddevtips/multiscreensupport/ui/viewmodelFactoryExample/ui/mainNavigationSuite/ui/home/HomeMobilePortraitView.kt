package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.mainNavigationSuite.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailViewState
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.article.data.Article
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.article.ui.list.ArticleListView
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.mainNavigationSuite.ui.home.data.HomeViewEvent

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun HomeMobilePortraitView(
    viewState: State<ListDetailViewState<Article>>,
    modifier: Modifier = Modifier,
    event: (HomeViewEvent) -> Unit
) {

    ArticleListView(
        viewState = viewState,
        modifier = modifier,
        event = event
    )
}