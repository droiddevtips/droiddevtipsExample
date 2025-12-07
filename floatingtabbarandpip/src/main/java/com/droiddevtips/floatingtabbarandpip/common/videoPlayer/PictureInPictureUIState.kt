package com.droiddevtips.floatingtabbarandpip.common.videoPlayer

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.app.PictureInPictureModeChangedInfo
import androidx.core.util.Consumer

/**
 * The picture-in-picture UI state
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun rememberPipUiState(): State<PictureInPictureModeChangedInfo?> {

    val pipUiState = remember { mutableStateOf<PictureInPictureModeChangedInfo?>(null) }

    val activity = LocalActivity.current
    var componentActivity: ComponentActivity? = null

    if (activity is ComponentActivity) {
        componentActivity = activity
    }

    DisposableEffect(activity) {

        val listener = Consumer<PictureInPictureModeChangedInfo> { newState ->
            pipUiState.value = newState
        }

        componentActivity?.addOnPictureInPictureModeChangedListener(listener = listener)

        onDispose {
            componentActivity?.removeOnPictureInPictureModeChangedListener(listener = listener)
        }
    }
    return pipUiState
}