package com.droiddevtips.admobnextgenads.feature.nativeAds

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import com.droiddevtips.admobnextgenads.common.ads.nativeAd.NativeAdImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun NativeAdNonVideo(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val loadNativeImageAd = remember { mutableStateOf(false) }
    val isLoadingAd = remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (loadNativeImageAd.value) {
            isLoadingAd.value = false
            NativeAdImage(adUnit = NextGenAdUnit.NativeAdImage)
        }

        if (isLoadingAd.value) {
            CircularProgressIndicator(modifier = Modifier.size(35.dp), strokeWidth = 1.dp)
        }

        Button(
            modifier = Modifier.padding(top = 20.dp),
            enabled = !loadNativeImageAd.value,
            onClick = {
                scope.launch {
                    if (loadNativeImageAd.value) {
                        loadNativeImageAd.value = false
                        isLoadingAd.value = true
                        delay(1000)
                        loadNativeImageAd.value = true
                    } else {
                        loadNativeImageAd.value = true
                    }
                }
            }) {
            Text("Load ad", color = if (loadNativeImageAd.value) Color.LightGray.copy(alpha = 0.28f) else Color.Unspecified)
        }
    }
}