package com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui.listView.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.BannerAdView
import com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui.listView.data.ListItem
import com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui.listView.data.ListViewState

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun InlineAdaptiveListView(
    modifier: Modifier = Modifier,
    viewState: State<ListViewState>
) {

    Box(modifier = modifier) {

        AnimatedVisibility(
            visible = viewState.value.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LoadingView()
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(items = viewState.value.items) {

                ListCustomCell(item = it)
            }
        }
    }
}

@Composable
private fun LoadingView() {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically)
        ) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
            Text("Loading data....", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun ListCustomCell(item: ListItem) {

    Row(modifier = Modifier.fillMaxWidth().padding(all = 16.dp)) {

        item.adUnit?.let {
            BannerAdView(bannerAdUnit = it)
        }?:run {
            Text(text = item.title)
        }
    }
}