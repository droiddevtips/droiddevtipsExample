package com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui.collapsible

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
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.CollapsibleBannerAdView
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.fetcher.CollapsibleType

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun BannerAdCollapsibleTop(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Next Gen Banner ad collapsible banner (Top)",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 16.dp)
        )

        CollapsibleBannerAdView(
            bannerAdUnit = NextGenAdUnit.CollapsibleBannerAd,
            collapsibleType = CollapsibleType.TOP
        )
    }
}