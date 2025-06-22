package com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.ui

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError
import com.google.android.libraries.ads.mobile.sdk.rewarded.OnUserEarnedRewardListener
import com.google.android.libraries.ads.mobile.sdk.rewarded.RewardItem
import com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAd
import com.google.android.libraries.ads.mobile.sdk.rewarded.RewardedAdEventCallback

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun RewardedAdView(
    rewardedAd: RewardedAd?,
    modifier: Modifier = Modifier,
    onAdDismissed: () -> Unit,
    onUserRewardCollected: () -> Unit
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
                onUserRewardCollected()
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