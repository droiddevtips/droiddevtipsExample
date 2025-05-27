package com.droiddevtips.admobnextgenads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droiddevtips.admobnextgenads.feature.main.Route
import com.droiddevtips.admobnextgenads.core.domain.navigateToView
import com.droiddevtips.admobnextgenads.feature.collapsible.CollapsibleAds
import com.droiddevtips.admobnextgenads.feature.fixedSize.FixedSizeAds
import com.droiddevtips.admobnextgenads.feature.inlineAdaptive.InlineAdaptive
import com.droiddevtips.admobnextgenads.feature.interstitialAds.InterstitialAds
import com.droiddevtips.admobnextgenads.feature.main.MainView
import com.droiddevtips.admobnextgenads.feature.nativeAds.NativeAds
import com.droiddevtips.admobnextgenads.feature.rewardedAds.RewardedAds
import com.droiddevtips.admobnextgenads.feature.rewardedInterstitialAds.RewardedInterstitialAds
import com.droiddevtips.typography.DroidDevTipsTheme

class AdMobNextGenExample : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {

                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = Route.InlineAdaptive.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable(route = Route.Home.route) {
                            MainView { route ->
                                navController.navigateToView(route = route)
                            }
                        }

                        composable(route = Route.InlineAdaptive.route) {
                            InlineAdaptive()
                        }

                        composable(route = Route.Collapsible.route) {
                            CollapsibleAds()
                        }

                        composable(route = Route.FixedSize.route) {
                            FixedSizeAds()
                        }

                        composable(route = Route.InterstitialAds.route) {
                            InterstitialAds()
                        }

                        composable(route = Route.NativeAds.route) {
                            NativeAds()
                        }

                        composable(route = Route.RewardedAds.route) {
                            RewardedAds()
                        }

                        composable(route = Route.RewardedInterstitialAds.route) {
                            RewardedInterstitialAds()
                        }
                    }
                }
            }
        }
    }
}