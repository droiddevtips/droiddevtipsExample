package com.droiddevtips.admobnextgenads.feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * The main view displaying the example buttons
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun MainView(
    modifier: Modifier = Modifier,
    enableComponent: State<Boolean>,
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
        Button(
            enabled = enableComponent.value,
            onClick = {
                navigate(Route.BannerAd.route)
            }
        ) {
            ButtonText(text = "Inline Adaptive banner ad", enable = enableComponent)
        }

        Button(
            enabled = enableComponent.value,
            onClick = {
                navigate(Route.InterstitialAds.route)
            }) {
            ButtonText(text = "Interstitial ad", enable = enableComponent)
        }

        Button(
            enabled = enableComponent.value,
            onClick = {
                navigate(Route.NativeAds.route)
            }) {
            ButtonText(text = "Native ad", enable = enableComponent)
        }

        Button(
            enabled = enableComponent.value,
            onClick = {
                navigate(Route.RewardedAds.route)
            }) {
            ButtonText(text = "Rewarded ad", enable = enableComponent)
        }
    }
}

@Composable
private fun ButtonText(text: String, enable: State<Boolean>) {
    Text(
        text = text,
        color = if (enable.value) Color.Unspecified else Color.LightGray.copy(alpha = 0.6f)
    )
}