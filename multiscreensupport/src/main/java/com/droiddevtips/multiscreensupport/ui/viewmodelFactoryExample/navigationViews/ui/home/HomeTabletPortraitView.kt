package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.article.ui.grid.ArticleGridView
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.data.HomeArticleViewState
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.data.HomeViewEvent

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun HomeTabletPortraitView(
    viewState: State<HomeArticleViewState>,
    modifier: Modifier = Modifier,
    event: (HomeViewEvent) -> Unit
) {
    ArticleGridView(
        viewState = viewState,
        modifier = modifier,
        event = event
    )
}