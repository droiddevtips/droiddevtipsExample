package com.droiddevtips.admobnextgenads.feature.bannerAd.ui.collapsible

import androidx.compose.runtime.Composable
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.CollapsibleBannerAdView
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.fetcher.CollapsibleType

/**
 * The banner ad collapsible bottom composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun BannerAdCollapsibleBottom() {
    CollapsibleBannerAdView(
        bannerAdUnit = NextGenAdUnit.CollapsibleBannerAd,
        collapsibleType = CollapsibleType.BOTTOM
    )
}