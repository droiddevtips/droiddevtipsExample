package com.droiddevtips.admobnextgenads.feature.rewardedAds.interstitial

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.droiddevtips.admobnextgenads.core.data.AppColor
import com.droiddevtips.admobnextgenads.core.data.AppString
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdListDisplayItem
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.ui.RewardedAdListItem
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.ui.ShimmerListView
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError
import com.google.android.libraries.ads.mobile.sdk.rewarded.OnUserEarnedRewardListener
import com.google.android.libraries.ads.mobile.sdk.rewarded.RewardItem
import com.google.android.libraries.ads.mobile.sdk.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.libraries.ads.mobile.sdk.rewardedinterstitial.RewardedInterstitialAdEventCallback

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun RewardedInterstitialAdsListView(
    viewState: State<RewardedInterstitialAdViewState>,
    action: (RewardedInterstitialAdViewAction) -> Unit,
    onItemClicked: (RewardedAdListDisplayItem) -> Unit,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()
    val lazyColumnListState = rememberLazyListState()

    Box(modifier = modifier.fillMaxSize()) {

        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = viewState.value.isLoading,
            enter = fadeIn(),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            ShimmerListView()
        }

        LazyColumn(
            state = lazyColumnListState,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            itemsIndexed(viewState.value.newsList) { index, listItem ->
                RewardedAdListItem(displayItem = listItem, onClick = {
                    if (listItem.premium) {
//                        action(
//                            RewardedInterstitialAdViewAction.ShowRewardedInterstitialAd(
//                                premiumArticleData = it
//                            )
//                        )
                        action(
                            RewardedInterstitialAdViewAction.ShowRewardedInterstitialAdDialog(premiumArticleData = it)
                        )
                    } else {
                        onItemClicked(it)
                    }
                })

                if (index < viewState.value.newsList.lastIndex) {
                    HorizontalDivider()
                } else {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                }
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = viewState.value.isLoadingAd,
            enter = fadeIn(),
            exit = fadeOut(animationSpec = tween(300))
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(id = AppColor.droid_dev_tips_green))
            ) {

                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(30.dp),
                        color = Color.White
                    )

                    Text("is loading rewarded ads.....")
                }
            }
        }

        if (viewState.value.showRewardedInterstitialDialog) {
            RewardedAdDialog(
                onAccept = {
                    action(RewardedInterstitialAdViewAction.ShowRewardedInterstitialAd)
                },
                onDismiss = {
                    action(RewardedInterstitialAdViewAction.DismissRewardedInterstitialAdDialog)
                }
            )
        }

        if (viewState.value.showRewardedInterstitial && viewState.value.rewardedInterstitialAdView != null) {
            viewState.value.rewardedInterstitialAdView?.let {
                RewardedInterstitialAdView(rewardedInterstitial = it, onCompletion = {
                    action(RewardedInterstitialAdViewAction.DismissRewardedInterstitialAd(rewardClaimed = true))
                }, onDismiss = {
                    action(RewardedInterstitialAdViewAction.DismissRewardedInterstitialAd())
                })
            }?:run {
                action(RewardedInterstitialAdViewAction.DismissRewardedInterstitialAd())
            }
        }

        if (viewState.value.navigateToDetail) {

            viewState.value.premiumArticleData?.let {
             onItemClicked(it)
            }

            action(RewardedInterstitialAdViewAction.ResetViewState)
        }
    }
}

@Composable
private fun RewardedAdDialog(
    onDismiss: () -> Unit,
    onAccept: () -> Unit
) {

    Dialog(onDismissRequest = onDismiss) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = stringResource(id = AppString.premium_article),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
            )

            Text(
                text = stringResource(id = AppString.premium_article_ad_message),
                style = MaterialTheme.typography.bodyMedium.copy(color = if (isSystemInDarkTheme()) Color.White else Color.Black)
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = {
                    onAccept()
                }) {
                    Text(
                        stringResource(id = AppString.yes),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                TextButton(onClick = {
                    onDismiss()
                }) {
                    Text(
                        stringResource(id = AppString.no),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun RewardedInterstitialAdView(
    rewardedInterstitial: RewardedInterstitialAd,
    onCompletion: () -> Unit,
    onDismiss: () -> Unit,
) {

    LocalActivity.current?.let { _activity ->

        rewardedInterstitial.apply {

            adEventCallback = object : RewardedInterstitialAdEventCallback {

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    println("[Rewarded Interstitial Ad] - Ad Showed FullScreen Content")
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    println("[Rewarded Interstitial Ad] - On Ad Dismissed FullScreen Content")
                    onDismiss()
                }

                override fun onAdFailedToShowFullScreenContent(fullScreenContentError: FullScreenContentError) {
                    super.onAdFailedToShowFullScreenContent(fullScreenContentError)
                    println("[Rewarded Interstitial Ad] - On Ad Failed to show FullScreen, cause: ${fullScreenContentError.message}")
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    println("[Rewarded Interstitial Ad] - On Ad Impression")
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    println("[Rewarded Interstitial Ad] - On Ad Clicked")
                }
            }

            show(
                activity = _activity,
                onUserEarnedRewardListener = object : OnUserEarnedRewardListener {
                    override fun onUserEarnedReward(reward: RewardItem) {
                        onCompletion()
                    }
                }
            )
        }
    }
}
