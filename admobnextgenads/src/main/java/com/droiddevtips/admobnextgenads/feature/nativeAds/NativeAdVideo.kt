package com.droiddevtips.admobnextgenads.feature.nativeAds

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Checkbox
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
import com.droiddevtips.admobnextgenads.common.ads.nativeAd.NativeAdVideoComposeView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * This is the native ad video composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun NativeAdVideo(modifier: Modifier = Modifier) {

    val scope = rememberCoroutineScope()
    val mutedVideo = remember { mutableStateOf(false) }
    val loadNativeVideoAd = remember { mutableStateOf(false) }
    val isLoadingAd = remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (loadNativeVideoAd.value) {
            isLoadingAd.value = false
            NativeAdVideoComposeView(
                adUnit = NextGenAdUnit.NativeAdVideo,
                mutedVideo = mutedVideo.value
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                enabled = !loadNativeVideoAd.value,
                checked = mutedVideo.value,
                onCheckedChange = {
                    mutedVideo.value = mutedVideo.value.not()
                })

            Text("Start video on mute", color = if (loadNativeVideoAd.value) Color.LightGray.copy(alpha = 0.28f) else Color.Unspecified)
        }

        if (isLoadingAd.value) {
            CircularProgressIndicator(modifier = Modifier.size(35.dp), strokeWidth = 1.dp)
        }

        Button(
            enabled = !loadNativeVideoAd.value,
            onClick = {
                scope.launch {
                    if (loadNativeVideoAd.value) {
                        loadNativeVideoAd.value = false
                        isLoadingAd.value = true
                        delay(1000)
                        loadNativeVideoAd.value = true
                    } else {
                        loadNativeVideoAd.value = true
                    }
                }
            }) {
            Text("Load ad", color = if (loadNativeVideoAd.value) Color.LightGray.copy(alpha = 0.28f) else Color.Unspecified)
        }
    }
}
