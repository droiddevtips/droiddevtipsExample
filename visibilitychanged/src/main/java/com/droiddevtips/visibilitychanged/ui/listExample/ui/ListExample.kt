package com.droiddevtips.visibilitychanged.ui.listExample.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutBoundsHolder
import androidx.compose.ui.layout.layoutBounds
import androidx.compose.ui.layout.onVisibilityChanged
import androidx.compose.ui.unit.dp
import com.droiddevtips.visibilitychanged.data.TabRoute

/**
 * A list example demonstrating the onVisibilityChanged API, showing how to track when list
 * items become visible or hidden during scrolling.
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun ListExample(
    modifier: Modifier = Modifier,
    onTabVisible: (String) -> Unit
) {
    val viewport = remember { LayoutBoundsHolder() }
    Box(
        modifier = modifier
            .layoutBounds(viewport)
            .onVisibilityChanged { visible ->
                if (visible) {
                    onTabVisible(TabRoute.List.title)
                }
            }
    ) {

        val dummyList = (0..100).toList()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(dummyList) { itemIndex ->

                Column {
                    val isVisible = remember { mutableStateOf(false) }

                    DummyListItem(
                        index = itemIndex,
                        isVisible = isVisible.value,
                        viewport = viewport,
                        modifier = Modifier.fillMaxWidth(),
                        onVisibilityChanged = {
                            isVisible.value = it
                        }
                    )

                    if (itemIndex % 10 == 0) {
                        DummyAdView(
                            index = itemIndex,
                            viewport = viewport,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}