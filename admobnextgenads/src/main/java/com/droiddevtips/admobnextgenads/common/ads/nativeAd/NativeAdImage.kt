package com.droiddevtips.admobnextgenads.common.ads.nativeAd

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.droiddevtips.admobnextgenads.common.ads.MobileAdsManager
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.droiddevtips.admobnextgenads.common.adsLoadingView.AdLoadingView
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun NativeAdImage(adUnit: NextGenAdUnit) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val isInPreview = LocalInspectionMode.current

    val showNativeAd = remember {
        mutableStateOf(true)
    }

    if (!showNativeAd.value)
        return

    val loadingViewHeight = remember {
        mutableStateOf(300.dp)
    }

    val isLoadingAds = remember { mutableStateOf(true) }
    var native_Ad by remember { mutableStateOf<NativeAd?>(null) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {

        if (isInPreview) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(color = Color.LightGray)
            )

            return
        }

        if (native_Ad != null) {
            NativeAdContainerView(nativeAd = native_Ad)
        }

        LaunchedEffect(Unit) {

            MobileAdsManager.fetchNativeImageAd(
                adUnit = adUnit
            ) { nativeVideoAd ->
                isLoadingAds.value = false
                native_Ad = nativeVideoAd
            }
        }

        AdLoadingView(
            visible = isLoadingAds.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(loadingViewHeight.value)
        )
    }

    DisposableEffect(lifecycleOwner, effect = {

        val observer = LifecycleEventObserver { _, event ->

            if (event == Lifecycle.Event.ON_ANY) {
                native_Ad?.destroy()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })
}