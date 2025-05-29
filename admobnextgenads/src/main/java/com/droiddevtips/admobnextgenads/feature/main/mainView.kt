package com.droiddevtips.admobnextgenads.feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * The main view displaying the example buttons
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun MainView(
    modifier: Modifier = Modifier,
    navigate: (String) -> Unit
) {

    val scrollable = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollable),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            16.dp,
            Alignment.CenterVertically
        )
    ) {
        Button(onClick = {
            navigate(Route.BannerAd.route)
        }) {
            Text(text = "Inline Adaptive banner ad")
        }

        Button(onClick = {
            navigate(Route.InterstitialAds.route)
        }) {
            Text("Interstitial ad")
        }

        Button(onClick = {
            navigate(Route.NativeAds.route)
        }) {
            Text("Native ad")
        }

        Button(onClick = {
            navigate(Route.RewardedAds.route)
        }) {
            Text("Rewarded ad")
        }

        Button(onClick = {
            navigate(Route.RewardedInterstitialAds.route)
        }) {
            Text("Rewarded interstitial ads")
        }
    }
}