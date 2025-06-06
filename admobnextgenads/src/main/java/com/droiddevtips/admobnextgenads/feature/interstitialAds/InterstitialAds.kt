package com.droiddevtips.admobnextgenads.feature.interstitialAds

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.droiddevtips.admobnextgenads.common.ads.MobileAdsManager
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.droiddevtips.admobnextgenads.common.adsLoadingView.AdLoadingView
import com.google.android.libraries.ads.mobile.sdk.common.AdValue
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError
import com.google.android.libraries.ads.mobile.sdk.interstitial.InterstitialAdEventCallback

/**
 * The interstitial ad composable
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun InterstitialAds() {

    val activity = LocalActivity.current
    val isLoadingAd = remember { mutableStateOf(false) }
    val interstitialAdUnit = NextGenAdUnit.InterstitialAd

    if (activity == null) {
        InterstitialAdViewFailedPlaceHolder()
        return
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Interstitial example")
            Button(
                enabled = !isLoadingAd.value,
                onClick = {
                    isLoadingAd.value = true
                    MobileAdsManager.fetchInterstitialAd(adUnit = interstitialAdUnit) { interstitialAd ->
                        isLoadingAd.value = false
                        interstitialAd?.let {

                            it.adEventCallback = object : InterstitialAdEventCallback {

                                override fun onAdShowedFullScreenContent() {
                                    super.onAdShowedFullScreenContent()
                                    println("[interstitial ad] - onAdShowedFullScreenContent")
                                }

                                override fun onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent()
                                    println("[interstitial ad] - onAdDismissedFullScreenContent")
                                }

                                override fun onAdClicked() {
                                    super.onAdClicked()
                                    println("[interstitial ad] - onAdClicked")
                                }

                                override fun onAdFailedToShowFullScreenContent(
                                    fullScreenContentError: FullScreenContentError
                                ) {
                                    super.onAdFailedToShowFullScreenContent(fullScreenContentError)
                                    println("[interstitial ad] - onAdFailedToShowFullScreenContent - ${fullScreenContentError.message}")
                                }

                                override fun onAdImpression() {
                                    super.onAdImpression()
                                    println("[interstitial ad] - onAdImpression")
                                }

                                override fun onAdPaid(value: AdValue) {
                                    super.onAdPaid(value)
                                    println("[interstitial ad] - onAdPaid - ${interstitialAdUnit.id}")
                                    println("[interstitial ad] - onAdPaid (micros: ${value.valueMicros})")
                                    println("[interstitial ad] - onAdPaid (currency code ${value.currencyCode})")
                                    println("[interstitial ad] - onAdPaid (precision type ${value.precisionType})")
                                }
                            }
                            it.show(activity)
                        } ?: run {
                            Toast.makeText(
                                activity,
                                "Failed loading interstitial ad",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            ) {
                Text("Show")
            }
        }

        AdLoadingView(
            visible = isLoadingAd.value,
            modifier = Modifier
                .fillMaxSize()
                .align(alignment = Alignment.Center)
        )
    }
}

@Composable
private fun InterstitialAdViewFailedPlaceHolder(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(color = Color.LightGray)
    ) {
        androidx.compose.material.Text(
            "Failed to load interstitial Ad View", color = Color.Black, modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}