@file:OptIn(ExperimentalMaterial3Api::class)

package com.droiddevtips.pinchtozoom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.droiddevtips.typography.DroidDevTipsTheme

/**
 * The main activity containing the pinch-to-zoom composable example
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class PinchToZoomActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {
                Scaffold(
                    containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.background else Color.LightGray,
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        TopSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                        )
                    }) { innerPadding ->

                    ZoomableBox(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.sample),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier

            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        InfoSection(
            icon = R.drawable.pinch,
            title = stringResource(id = R.string.pinch_zoom_in_out)
        )

        InfoSection(
            icon = R.drawable.double_tab,
            title = stringResource(id = R.string.double_tab_zoom_back)
        )
    }
}