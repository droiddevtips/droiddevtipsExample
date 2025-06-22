@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.droiddevtips.admobnextgenads.R
import com.droiddevtips.admobnextgenads.core.data.AppString
import com.droiddevtips.admobnextgenads.extensions.pulseEffect
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdListDisplayItem
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdViewAction
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdViewState

/**
 * This is the rewarded ads composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun RewardedAdsArticleListView(
    viewState: State<RewardedAdViewState>,
    action: (RewardedAdViewAction) -> Unit,
    onItemClicked: (RewardedAdListDisplayItem) -> Unit,
    modifier: Modifier = Modifier
) {

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
                RewardedAdListItem(displayItem = listItem, onClick = onItemClicked)

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
            visible = !viewState.value.isLoading,
            enter = fadeIn(),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomEnd = 0.dp, bottomStart = 0.dp), color = colorResource(id = R.color.droid_dev_tips_green).copy(alpha = 0.95f)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1.0f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    if (viewState.value.showRewardedAdButton) {

                        if (viewState.value.isLoadingAd) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(30.dp),
                                color = Color.White
                            )
                        } else {
                            TextButton(onClick = {
                                action(RewardedAdViewAction.LoadAndShowRewardedAd)
                            }) {
                                Text(
                                    text = stringResource(id = AppString.more_credit_message),
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier
                                        .pulseEffect(),
                                    color = Color.White
                                )
                            }
                        }

                    }
                }

                Column(
                    modifier = Modifier.size(50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("Credit", style = MaterialTheme.typography.labelSmall, color = Color.White, modifier = Modifier.padding(top = 3.dp))

                    Box(modifier = Modifier.size(35.dp)) {
                        Text(
                            "${viewState.value.credit}",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }

    if (viewState.value.showRewardedAd) {
        viewState.value.rewardedAdView?.let {
            RewardedAdView(rewardedAd = it, onAdDismissed = {
                action(RewardedAdViewAction.DismissRewardedAd)
            }, onUserRewardCollected = {
                action(RewardedAdViewAction.OnRewardedAdCompleted)
            })
        } ?: run {
            action(RewardedAdViewAction.DismissRewardedAd)
        }
    }
}