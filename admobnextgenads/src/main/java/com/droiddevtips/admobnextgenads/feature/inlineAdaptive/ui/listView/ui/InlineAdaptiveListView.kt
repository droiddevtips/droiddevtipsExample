package com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui.listView.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.droiddevtips.admobnextgenads.common.ads.MobileAdsManager
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.BannerAdView
import com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui.listView.data.ListItem
import com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui.listView.data.ListViewState
import com.droiddevtips.typography.Drawable

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun InlineAdaptiveListView(
    modifier: Modifier = Modifier,
    viewState: State<ListViewState>
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_ANY) {
                MobileAdsManager.clearAllCachedBanner()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    Box(modifier = modifier) {

        AnimatedVisibility(
            visible = viewState.value.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LoadingView()
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

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
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
            Text("Loading data....", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun ListCustomCell(item: ListItem) {

    item.adUnit?.let {
        BannerAdView(bannerAdUnit = it)
    } ?: run {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(all = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .border(width = 1.dp, color = Color.LightGray, CircleShape),
                painter = painterResource(Drawable.droid_dev_tips_logo),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Text(text = item.title)
        }
    }
}