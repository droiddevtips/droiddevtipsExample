package com.droiddevtips.multiscreensupport.ui.rememberExample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

/**
 * The composable remember example
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun RememberExample(modifier: Modifier = Modifier) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val number = remember { mutableIntStateOf(1) }

    val width = when (windowSizeClass.windowWidthSizeClass) {

        WindowWidthSizeClass.COMPACT -> WindowWidthSizeClass.COMPACT.toString()
        WindowWidthSizeClass.MEDIUM -> WindowWidthSizeClass.MEDIUM.toString()
        WindowWidthSizeClass.EXPANDED -> WindowWidthSizeClass.EXPANDED.toString()
        else -> "Unknown width"
    }

    val height = when (windowSizeClass.windowHeightSizeClass) {

        WindowHeightSizeClass.COMPACT -> WindowHeightSizeClass.COMPACT.toString()
        WindowHeightSizeClass.MEDIUM -> WindowHeightSizeClass.MEDIUM.toString()
        WindowHeightSizeClass.EXPANDED -> WindowHeightSizeClass.EXPANDED.toString()
        else -> "Unknown height"
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp, alignment = Alignment.CenterVertically)
    ) {
        Text(
            text = "$width\n$height",
            modifier = modifier
        )

        Text(
            text = "${number.intValue}"
        )

        Button(onClick = {
            number.intValue = ++number.intValue
        }) {
            Text("Increase number")
        }
    }
}
