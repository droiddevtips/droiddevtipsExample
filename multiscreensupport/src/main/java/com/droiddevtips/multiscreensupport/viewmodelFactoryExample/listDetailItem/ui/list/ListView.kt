package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
fun ListView(
    viewState: State<ListDetailViewState>,
    viewType: ViewType,
    modifier: Modifier = Modifier,
    event: (ListDetailEvent) -> Unit
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
            items(viewState.value.itemsList) { item ->
                ListItem(item = item, viewType = viewType) {
                    event(ListDetailEvent.NavigateToDetail(item = it))
                }
            }
        }

        AnimatedVisibility(
            visible = viewState.value.showEmptyView,
            enter = fadeIn(),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            EmptyView(modifier = Modifier.fillMaxSize())
        }

        AnimatedVisibility(
            visible = viewState.value.showLoadingView,
            enter = fadeIn(),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            ListDetailLoadingView()
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow {
            lazyListState.firstVisibleItemIndex
        }.debounce(500L)
            .collectLatest { index ->
                event(ListDetailEvent.SetScrollPosition(index = index))
            }
    }

    LaunchedEffect(viewState.value.visibleIndex) {
        if (viewState.value.visibleIndex != lazyListState.firstVisibleItemIndex) {
            lazyListState.animateScrollToItem(index = viewState.value.visibleIndex)
        }
    }
}