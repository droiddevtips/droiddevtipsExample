package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.article.ui.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailViewState
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.article.data.Article
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.article.ui.ArticleLoadingView
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.mainNavigationSuite.ui.home.data.HomeViewEvent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@OptIn(FlowPreview::class)
@Composable
fun ArticleListView(
    viewState: State<ListDetailViewState<Article>>,
    modifier: Modifier = Modifier,
    event: (HomeViewEvent) -> Unit
) {

    val lazyListState = rememberLazyListState(
        initialFirstVisibleItemIndex = viewState.value.visibleIndex
    )

    Box(modifier = modifier) {

        LazyColumn(
            state = lazyListState,
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 16.dp)
        ) {
            items(viewState.value.itemsList) { article ->
                ArticleListItem(article = article) {
                    event(HomeViewEvent.NavigateToDetail(article = article))
                }
            }
        }

        AnimatedVisibility(
            visible = viewState.value.showLoadingView,
            enter = fadeIn(),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            ArticleLoadingView()
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.firstVisibleItemIndex
        }.debounce(500L)
            .collectLatest { index ->
                event(HomeViewEvent.SetScrollPosition(index = index))
            }
    }

    LaunchedEffect(viewState.value.visibleIndex) {
        if (viewState.value.visibleIndex != lazyListState.firstVisibleItemIndex) {
            lazyListState.animateScrollToItem(index = viewState.value.visibleIndex)
        }
    }
}