package com.droiddevtips.admobnextgenads.feature.rewardedAds.interstitial.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droiddevtips.admobnextgenads.common.ads.MobileAdsManager
import com.droiddevtips.admobnextgenads.core.domain.navigateToView
import com.droiddevtips.admobnextgenads.feature.rewardedAds.interstitial.data.RewardedInterstitialAdNewsRepositoryImpl
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdListDisplayItem
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdViewRoute
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.ui.ArticleDetailScreen

/**
 * The rewarded interstitial composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun RewardedInterstitial(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val dataStateKey = "data"

    Box(modifier = modifier.fillMaxSize()) {

        NavHost(
            navController = navController,
            startDestination = RewardedAdViewRoute.RewardedAdsArticleList.route
        ) {

            composable(route = RewardedAdViewRoute.RewardedAdsArticleList.route) {
                val rewardedAdViewModel: RewardedInterstitialViewModel =
                    viewModel(
                        factory = RewardedInterstitialAdViewModelFactory(
                            repository = RewardedInterstitialAdNewsRepositoryImpl(),
                            adFetcher = MobileAdsManager
                        )
                    )
                val viewState =
                    rewardedAdViewModel.rewardedInterstitialAdViewState.collectAsStateWithLifecycle()
                RewardedInterstitialAdsListView(
                    viewState = viewState,
                    action = rewardedAdViewModel::performAction,
                    onItemClicked = {

                        if (viewState.value.isLoadingAd)
                            return@RewardedInterstitialAdsListView

                        navController.currentBackStackEntry?.savedStateHandle?.set(dataStateKey, it)
                        navController.navigateToView(RewardedAdViewRoute.RewardedAdsArticleDetail.route)
                    })
            }

            composable(
                route = RewardedAdViewRoute.RewardedAdsArticleDetail.route
            ) {
                val data =
                    navController.previousBackStackEntry?.savedStateHandle?.get<RewardedAdListDisplayItem>(
                        dataStateKey
                    )
                ArticleDetailScreen(
                    data = data
                )
            }
        }
    }

}