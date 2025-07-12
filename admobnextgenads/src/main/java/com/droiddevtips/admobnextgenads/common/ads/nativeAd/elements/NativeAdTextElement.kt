package com.droiddevtips.admobnextgenads.common.ads.nativeAd.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

/**
 * The native ad text element composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun NativeAdTextElement(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier.background(
            color = Color(
                0xFFFFCC66
            )
        )
    ) {
        Text(
            "Ad",
            fontSize = 10.sp,
            color = Color.White,
            lineHeight = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}