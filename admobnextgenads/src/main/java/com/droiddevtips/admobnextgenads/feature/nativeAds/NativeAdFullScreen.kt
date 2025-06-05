package com.droiddevtips.admobnextgenads.feature.nativeAds

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.droiddevtips.admobnextgenads.common.ads.nativeAd.NativeAdFullscreenView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun NativeAdFullscreen(modifier: Modifier = Modifier) {

    val scope = rememberCoroutineScope()
    val loadNativeImageAd = remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (loadNativeImageAd.value) {
            NativeAdFullscreenView(adUnit = NextGenAdUnit.NativeAdFullscreen)
        }

        Button(
            modifier = Modifier.padding(top = 20.dp),
            enabled = !loadNativeImageAd.value,
            onClick = {
                scope.launch {
                    if (loadNativeImageAd.value) {
                        loadNativeImageAd.value = false
                        delay(1000)
                        loadNativeImageAd.value = true
                    } else {
                        loadNativeImageAd.value = true
                    }
                }
            }) {
            Text("Load fullscreen native ad", color = if (loadNativeImageAd.value) Color.LightGray.copy(alpha = 0.28f) else Color.Unspecified)
        }
    }

}