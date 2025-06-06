package com.droiddevtips.admobnextgenads.common.ads

import android.content.Context
import android.widget.Toast
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.fetcher.AdFetcher
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.fetcher.AdFetcherImp
import com.google.android.libraries.ads.mobile.sdk.MobileAds
import com.google.android.libraries.ads.mobile.sdk.initialization.AdapterStatus
import com.google.android.libraries.ads.mobile.sdk.initialization.InitializationConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The mobile ads manager
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
object MobileAdsManager: AdFetcher by AdFetcherImp() {

    var initialized = false
        private set

    var deviceScreenWidth: Int = 300
        private set

    val adsApplicationID = "ca-app-pub-3940256099942544~3347511713" // Sample AdMob app ID, replace with your own adMob ID if you have one

    fun init(context: Context) {

        CoroutineScope(Dispatchers.IO).launch {

            calculateDeviceScreenWidth(context = context)

            // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
            val initializationConfig =
                InitializationConfig.Builder(adsApplicationID)
                    .build()

            MobileAds.initialize(
                context = context,
                initializationConfig = initializationConfig
            ) { status ->

                status.adapterStatusMap.forEach { (key, adapterStatus) ->
                    println("$key -> $adapterStatus")
                }

                status.adapterStatusMap["com.google.android.libraries.ads.mobile.sdk.MobileAds"]?.let { adapterStatusResult ->

                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(
                            context,
                            adapterStatusResult.description,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    if (adapterStatusResult.initializationState == AdapterStatus.InitializationState.COMPLETE) {
                        initialized = true
                        println("Mobile Ads initialized")
                    } else {
                        initialized = false
                        println("Failed to initialized mobile Ads, error: ${adapterStatusResult.description}")
                    }
                }
            }
        }
    }

    private fun calculateDeviceScreenWidth(context: Context) {
        val displayMetrics = context.resources.displayMetrics
        val widthInDp = displayMetrics.widthPixels / displayMetrics.density
        deviceScreenWidth = widthInDp.toInt()
    }
}