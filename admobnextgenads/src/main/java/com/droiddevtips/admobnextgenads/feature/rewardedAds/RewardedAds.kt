package com.droiddevtips.admobnextgenads.feature.rewardedAds

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.droiddevtips.admobnextgenads.common.ads.MobileAdsManager
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError
import com.google.android.libraries.ads.mobile.sdk.rewarded.OnUserEarnedRewardListener
import com.google.android.libraries.ads.mobile.sdk.rewarded.RewardItem
import com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd
import com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAdEventCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * This is the rewarded ads composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun RewardedAds(modifier: Modifier = Modifier) {

    val scope = rememberCoroutineScope()
    val isLoadingAd = remember { mutableStateOf(false) }
    val enableActionButton = remember { mutableStateOf(true) }
    val rewarded_Ad = remember { mutableStateOf<RewardedAd?>(null) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (rewarded_Ad.value != null) {
            isLoadingAd.value = false
            enableActionButton.value = false
            RewardedAdView(rewardedAd = rewarded_Ad.value, onAdDismissed = {
                println("[Rewarded Ad] - On dismissed callback")
                rewarded_Ad.value = null
                enableActionButton.value = true
            })
        }

        if (isLoadingAd.value) {
            CircularProgressIndicator(modifier = Modifier.size(35.dp), strokeWidth = 1.dp)
        }

        Button(
            modifier = Modifier.padding(top = 20.dp),
            enabled = enableActionButton.value,
            onClick = {
                scope.launch(Dispatchers.Main) {
                    if (rewarded_Ad.value == null) {
                        enableActionButton.value = false
                        isLoadingAd.value = true
                        MobileAdsManager.fetchRewardedAd(adUnit = NextGenAdUnit.RewardedAd) { rewardedAd ->
                            rewardedAd?.let {
                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(3000)
                                    rewarded_Ad.value = rewardedAd
                                }
                            }
                        }
                    }
                }
            }) {
            Text(
                "Load Rewarded ad",
                color = if (enableActionButton.value) Color.Unspecified else Color.LightGray.copy(
                    alpha = 0.28f
                )
            )
        }
    }
}

@Composable
private fun RewardedAdView(
    rewardedAd: RewardedAd?,
    modifier: Modifier = Modifier,
    onAdDismissed: () -> Unit
) {

    if (rewardedAd == null) {
        FailedToLoadPlaceholder(modifier = modifier)
        onAdDismissed()
        return
    }

    LocalActivity.current?.let { _activity ->

        rewardedAd.adEventCallback = object : RewardedAdEventCallback {

            override fun onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent()
                println("[Rewarded Ad] - Ad Showed FullScreen Content")
            }

            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                println("[Rewarded Ad] - On Ad Dismissed FullScreen Content")
                onAdDismissed()
            }

            override fun onAdFailedToShowFullScreenContent(fullScreenContentError: FullScreenContentError) {
                super.onAdFailedToShowFullScreenContent(fullScreenContentError)
                println("[Rewarded Ad] - On Ad Failed to show FullScreen, cause: ${fullScreenContentError.message}")
            }

            override fun onAdImpression() {
                super.onAdImpression()
                println("[Rewarded Ad] - On Ad Impression")
            }

            override fun onAdClicked() {
                super.onAdClicked()
                println("[Rewarded Ad] - On Ad Clicked")
            }
        }

        rewardedAd.show(_activity, object : OnUserEarnedRewardListener {

            override fun onUserEarnedReward(reward: RewardItem) {
                println("[Rewarded Ad] - On User Earned Reward")
                println("[Rewarded Ad] - type: ${reward.type}")
                println("[Rewarded Ad] - amount: ${reward.amount}")
            }
        })
    } ?: run {
        FailedToLoadPlaceholder(modifier = modifier)
        onAdDismissed()
    }
}

@Composable
private fun FailedToLoadPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(color = Color.LightGray)
    ) {
        Text(
            "Failed loading rewarded ad",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}
