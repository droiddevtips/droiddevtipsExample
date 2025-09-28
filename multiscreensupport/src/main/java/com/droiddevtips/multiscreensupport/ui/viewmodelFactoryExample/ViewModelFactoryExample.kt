package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.droiddevtips.multiscreensupport.data.MultiScreenSupportAction

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun ViewModelFactoryExample(modifier: Modifier = Modifier) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val multiScreenViewModel: MultiScreenViewModel = viewModel(
        factory =
            MultiScreenViewModelFactory()
    )

    val counter = multiScreenViewModel.counter.collectAsStateWithLifecycle()

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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp, alignment = Alignment.CenterVertically)
    ) {

        Text(
            text = "$width\n$height",
            modifier = modifier
        )

        Text(
            text = "VM counter ${counter.value}"
        )

        Button(onClick = {
            multiScreenViewModel.performAction(MultiScreenSupportAction.Increase)
        }) {
            Text("Increase number VM")
        }
    }
}