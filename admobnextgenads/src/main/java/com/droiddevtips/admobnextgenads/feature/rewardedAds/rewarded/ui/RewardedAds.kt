@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.ui

import android.widget.Toast
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droiddevtips.admobnextgenads.common.ads.MobileAdsManager
import com.droiddevtips.admobnextgenads.core.data.AppString
import com.droiddevtips.admobnextgenads.core.domain.navigateToView
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdListDisplayItem
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdViewAction
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdViewRoute
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.repository.RewardedAdNewsRepositoryImpl

/**
 * The rewarded ad composable that contains the [NavHost]
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun RewardedAd(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val context = LocalContext.current
    val dataStateKey = "data"

    Box(modifier = modifier.fillMaxSize()) {

        NavHost(navController = navController, startDestination = RewardedAdViewRoute.RewardedAdsArticleList.route) {

            composable(route = RewardedAdViewRoute.RewardedAdsArticleList.route) {
                val rewardedAdViewModel: RewardedAdViewModel =
                    viewModel(factory = RewardedAdViewModelFactory(repository = RewardedAdNewsRepositoryImpl(), adFetcher = MobileAdsManager))
                val viewState =
                    rewardedAdViewModel.rewardedAdViewState.collectAsStateWithLifecycle()
                RewardedAdsArticleListView(
                    viewState = viewState,
                    action = rewardedAdViewModel::performAction,
                    onItemClicked = {

                        if (viewState.value.isLoadingAd)
                            return@RewardedAdsArticleListView

                        if (it.premium && viewState.value.credit == 0) {
                            Toast.makeText(context, context.getString(AppString.no_sufficient_credit), Toast.LENGTH_LONG).show()
                            return@RewardedAdsArticleListView
                        }

                        navController.currentBackStackEntry?.savedStateHandle?.set(dataStateKey, it)
                        navController.navigateToView(RewardedAdViewRoute.RewardedAdsArticleDetail.route)

                        if (it.premium) {
                            rewardedAdViewModel.performAction(action = RewardedAdViewAction.SubtractCredit)
                        }
                    })
            }

            composable(
                route = RewardedAdViewRoute.RewardedAdsArticleDetail.route
            ) {
                val data = navController.previousBackStackEntry?.savedStateHandle?.get<RewardedAdListDisplayItem>(dataStateKey)
                ArticleDetailScreen(
                    data = data
                )
            }
        }
    }
}