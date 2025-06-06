package com.droiddevtips.admobnextgenads.feature.bannerAd.ui.anchored

import androidx.compose.runtime.Composable
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.BannerAdAnchoredView

/**
 * The banner ad anchored composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun BannerAdAnchored() {

    BannerAdAnchoredView(bannerAdUnit = NextGenAdUnit.AnchoredBannerAd)

}