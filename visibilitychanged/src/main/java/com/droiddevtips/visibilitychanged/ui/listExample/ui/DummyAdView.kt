package com.droiddevtips.visibilitychanged.ui.listExample.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutBoundsHolder
import androidx.compose.ui.layout.onVisibilityChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.droiddevtips.visibilitychanged.data.AppString

/**
 * An animated dummy ad composable used to demonstrate the onVisibilityChanged API.
 * When this dummy ad view becomes visible it animates its content and triggers a fake impression event by showing a toast (e.g., “Ads impression for Ad at index: $index”).
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun DummyAdView(
    index: Int,
    viewport: LayoutBoundsHolder,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val adsVisible = remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .heightIn(min = 1.dp, max = 300.dp)
            .onVisibilityChanged(
                minFractionVisible = 1f,
                minDurationMs = 2000,
                viewportBounds = viewport
            ) { visible ->
                if (visible)
                    adsVisible.value = true
            }
    ) {
        AnimatedVisibility(
            visible = adsVisible.value,
            enter = expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(),
            exit = slideOutVertically(),
            modifier = Modifier.fillMaxWidth()
        ) {
            val toastMessage = stringResource(AppString.dummy_ad_view_impression_toast_message, index)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
                    .height(300.dp)
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .onVisibilityChanged(
                        minFractionVisible = 1f,
                        minDurationMs = 1000,
                        viewportBounds = viewport
                    ) { visible ->
                        if (visible) {
                            Toast.makeText(
                                context, toastMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray)
                ) {
                    Text(
                        text = stringResource(AppString.dummy_ad_view_title, index),
                        color = Color.Black,
                        modifier = Modifier.align(alignment = Alignment.Center)
                    )
                }
            }
        }
    }
}