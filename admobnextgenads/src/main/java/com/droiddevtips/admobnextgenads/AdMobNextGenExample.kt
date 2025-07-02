package com.droiddevtips.admobnextgenads

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droiddevtips.admobnextgenads.common.ads.MobileAdsManager
import com.droiddevtips.admobnextgenads.core.domain.navigateToView
import com.droiddevtips.admobnextgenads.feature.bannerAd.ui.BannerAd
import com.droiddevtips.admobnextgenads.feature.interstitialAds.InterstitialAds
import com.droiddevtips.admobnextgenads.feature.main.MainView
import com.droiddevtips.admobnextgenads.feature.main.Route
import com.droiddevtips.admobnextgenads.feature.nativeAds.NativeAds
import com.droiddevtips.admobnextgenads.feature.rewardedAds.interstitial.RewardedInterstitial
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.ui.RewardedAd
import com.droiddevtips.typography.DroidDevTipsTheme
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentInformation.OnConsentInfoUpdateFailureListener
import com.google.android.ump.ConsentInformation.OnConsentInfoUpdateSuccessListener
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdMobNextGenExample : ComponentActivity() {

    private var consentInformation: ConsentInformation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DroidDevTipsTheme {

                val enableComponent = remember { mutableStateOf(false) }
                val showIsInitializing = remember { mutableStateOf(true) }
                val navController = rememberNavController()

                initialAdMobComponent { succeed ->
                    CoroutineScope(Dispatchers.Main).launch {
                        enableComponent.value = succeed
                        showIsInitializing.value = !succeed
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {

                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                        NavHost(
                            navController = navController,
                            startDestination = Route.Home.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {

                            composable(route = Route.Home.route) {
                                MainView(enableComponent = enableComponent) { route ->
                                    navController.navigateToView(route = route)
                                }
                            }

                            composable(route = Route.BannerAd.route) {
                                BannerAd()
                            }

                            composable(route = Route.InterstitialAds.route) {
                                InterstitialAds()
                            }

                            composable(route = Route.NativeAds.route) {
                                NativeAds()
                            }

                            composable(route = Route.RewardedAds.route) {
                                RewardedAd()
                            }

                            composable(route = Route.RewardedInterstitialAds.route) {
                                RewardedInterstitial()
                            }
                        }
                    }

                    AnimatedVisibility(
                        visible = showIsInitializing.value,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colorScheme.surfaceContainer),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                4.dp,
                                alignment = Alignment.CenterVertically
                            )
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(50.dp))
                            Text("Initializing adMob....", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    }

    private fun initialAdMobComponent(succeed: (Boolean) -> Unit) {
        initAdMob { initialized ->
            if (initialized) {
                launchUMP(succeed)
            } else {
                succeed(false)
            }
        }
    }

    private fun initAdMob(succeed: (Boolean) -> Unit) {
        MobileAdsManager.init(context = applicationContext, succeed = succeed)
    }

    private fun launchUMP(onFormLoaded: (Boolean) -> Unit) {

        if (consentInformation == null) {
            consentInformation = UserMessagingPlatform.getConsentInformation(applicationContext)
        }

        val params = ConsentRequestParameters.Builder().build()

        consentInformation?.requestConsentInfoUpdate(
            this@AdMobNextGenExample,
            params,
            object : OnConsentInfoUpdateSuccessListener {
                override fun onConsentInfoUpdateSuccess() {
                    println("[Ads] - onConsentInfoUpdateSuccess")
                    UserMessagingPlatform.loadAndShowConsentFormIfRequired(this@AdMobNextGenExample) { formError ->
                        println("[Ads] - form error: ${formError?.message}")
                        if (formError != null) {
                            Toast.makeText(
                                applicationContext,
                                "Error loading consentForm: ${formError.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            onFormLoaded(false)
                        } else {
                            onFormLoaded(true)
                        }
                    }
                }
            },
            object : OnConsentInfoUpdateFailureListener {
                override fun onConsentInfoUpdateFailure(p0: FormError) {
                    println("[Ads] - ${p0.message}")
                    onFormLoaded(true)
                }
            }
        )
    }
}

