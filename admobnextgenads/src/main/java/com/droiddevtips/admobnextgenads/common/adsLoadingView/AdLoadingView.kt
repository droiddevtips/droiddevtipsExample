package com.droiddevtips.admobnextgenads.common.adsLoadingView

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun AdLoadingView(visible: Boolean, modifier: Modifier = Modifier, frameModifier: Modifier = Modifier) {

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
                    CircularProgressIndicator(modifier = Modifier.size(50.dp))
                    Text("Loading ads....", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}