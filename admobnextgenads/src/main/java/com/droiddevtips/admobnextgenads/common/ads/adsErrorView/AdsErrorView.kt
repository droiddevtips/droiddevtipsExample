package com.droiddevtips.admobnextgenads.common.ads.adsErrorView

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * The generic ad error composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun AdErrorView(
    visible: Boolean,
    modifier: Modifier = Modifier,
    frameModifier: Modifier = Modifier
) {

    AnimatedVisibility(
        modifier = frameModifier,
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(modifier = modifier.background(color = if (isSystemInDarkTheme()) Color(0xFF717171) else Color(0xFFEAEAEA))) {

            Column(modifier = Modifier.align(Alignment.Center)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        16.dp,
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    Text("Error loading ads", style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red))
                }
            }
        }
    }
}