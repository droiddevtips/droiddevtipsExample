package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.grid

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailEvent
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailViewState
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ViewType
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.emptyView.EmptyView
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.loading.ListDetailLoadingView
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@OptIn(FlowPreview::class)
@Composable
fun GridView(
    viewState: State<ListDetailViewState>,
    viewType: ViewType,
    modifier: Modifier = Modifier,
    event: (ListDetailEvent) -> Unit
) {
    val lazyGridState = rememberLazyGridState(initialFirstVisibleItemIndex = viewState.value.visibleIndex)

    Box(modifier = modifier) {

        LazyVerticalGrid(
            state = lazyGridState,
            modifier = modifier,
            columns = GridCells.Adaptive(minSize = 260.dp)
        ) {
            items(viewState.value.itemsList) { item ->
                GridItem(item = item, viewType = viewType) {
                    event(ListDetailEvent.NavigateToDetail(item = item))
                }
            }
        }

        AnimatedVisibility(
            visible = viewState.value.showLoadingView,
            enter = fadeIn(),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            ListDetailLoadingView()
        }

        AnimatedVisibility(
            visible = viewState.value.showEmptyView,
            enter = fadeIn(),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            EmptyView(modifier = Modifier.fillMaxSize())
        }

        LaunchedEffect(lazyGridState) {
            snapshotFlow {
                lazyGridState.firstVisibleItemIndex
            }.debounce(500L)
                .collectLatest { index ->
                    event(ListDetailEvent.SetScrollPosition(index = index))
                }
        }

        LaunchedEffect(viewState.value.visibleIndex) {
            if (viewState.value.visibleIndex != lazyGridState.firstVisibleItemIndex) {
                lazyGridState.animateScrollToItem(index = viewState.value.visibleIndex)
            }
        }
    }
}