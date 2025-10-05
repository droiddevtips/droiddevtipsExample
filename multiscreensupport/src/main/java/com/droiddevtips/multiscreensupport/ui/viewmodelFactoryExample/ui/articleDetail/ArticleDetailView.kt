package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.articleDetail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun ArticleDetailView(modifier: Modifier = Modifier) {

    val density = LocalDensity.current
    val scrollable = rememberScrollState()
    val imageBoxActualHeight = rememberSaveable { mutableIntStateOf(200) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(scrollable),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = 0.6f)
                .height(imageBoxActualHeight.intValue.dp)
                .background(color = Color.DarkGray)
                .onSizeChanged({ size ->

                    Log.i("TAG35", "Landscape content size: $size")

                    val widthInDp = with(density) {
                        size.width.toDp()
                    }

                    val actualImageHeight = (widthInDp.value / 100) * 78
                    imageBoxActualHeight.intValue = actualImageHeight.toInt()
                    Log.i("TAG35", "Landscape content Width in dp: $widthInDp")
                    Log.i(
                        "TAG35",
                        "Landscape content actual Height in dp: ${imageBoxActualHeight.intValue}"
                    )
                })
        )

        Text("Title", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text("Content section")
    }
}