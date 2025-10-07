package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.article.ui.grid

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
fun ArticleGridView(
    viewState: State<ListDetailViewState<Article>>,
    modifier: Modifier = Modifier,
    event: (HomeViewEvent) -> Unit
) {

    val lazyGridState = rememberLazyGridState(initialFirstVisibleItemIndex = viewState.value.visibleIndex)

    Box(modifier = modifier) {

        LazyVerticalGrid(
            state = lazyGridState,
            modifier = modifier,
            columns = GridCells.Adaptive(minSize = 260.dp)
        ) {
            items(viewState.value.itemsList) { article ->
                ArticleGridItem(article = article,onItemClicked = {
                    event(HomeViewEvent.NavigateToDetail(article = article))
                })
            }
        }

        AnimatedVisibility(
            visible = viewState.value.showLoadingView,
            enter = fadeIn(),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            ArticleLoadingView()
        }

        LaunchedEffect(lazyGridState) {
            snapshotFlow {
                lazyGridState.firstVisibleItemIndex
            }.debounce(500L)
                .collectLatest { index ->
                    event(HomeViewEvent.SetScrollPosition(index = index))
                }
        }

        LaunchedEffect(viewState.value.visibleIndex) {
            if (viewState.value.visibleIndex != lazyGridState.firstVisibleItemIndex) {
                lazyGridState.animateScrollToItem(index = viewState.value.visibleIndex)
            }
        }
    }
}