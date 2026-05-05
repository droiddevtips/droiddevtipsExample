package com.droiddevtips.spotlight.main.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.spotlight.main.data.NewsItem

/**
 * The news list composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun NewsListView(
    newsList: List<NewsItem>,
    modifier: Modifier = Modifier,
    onClicked: (NewsItem) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(newsList) {
            NewsListItem(newsItem = it, onClicked = onClicked)
        }
    }
}