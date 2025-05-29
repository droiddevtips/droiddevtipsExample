package com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui.staticView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.BannerAdView
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun InlineAdaptiveStatic(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Next Gen Banner ad on static view", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(top = 16.dp))
        BannerAdView(bannerAdUnit = NextGenAdUnit.BannerAd(""))
    }
}