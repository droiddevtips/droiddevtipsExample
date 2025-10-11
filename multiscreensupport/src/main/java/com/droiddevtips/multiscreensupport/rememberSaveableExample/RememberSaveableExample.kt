package com.droiddevtips.multiscreensupport.rememberSaveableExample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.util.AppWindowSize
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.util.currentWindowSize

/**
 * The remember saveable composable example
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun RememberSaveableExample(modifier: Modifier = Modifier) {

    val currentWindowSize = currentWindowSize()
    val number = rememberSaveable { mutableIntStateOf(1) }

    val width = when(currentWindowSize.windowWidthSize)  {
        AppWindowSize.Compact -> "Compact"
        AppWindowSize.Medium -> "Medium"
        AppWindowSize.Expanded -> "Expanded"
    }

    val height = when(currentWindowSize.windowHeightSize) {
        AppWindowSize.Compact -> "Compact"
        AppWindowSize.Medium -> "Medium"
        AppWindowSize.Expanded -> "Expanded"
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