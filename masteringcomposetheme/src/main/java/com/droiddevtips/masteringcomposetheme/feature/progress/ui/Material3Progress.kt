@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.droiddevtips.masteringcomposetheme.feature.progress.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.masteringcomposetheme.feature.customCard.ui.AppCustomCard

/**
 * This is the Material 3 progress examples
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun Material3Progress(modifier: Modifier = Modifier) {

    AppCustomCard(modifier = modifier, title = "Progress") {
        CircularProgress(modifier = modifier)
    }

}


@Composable
private fun CircularProgress(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CircularProgressIndicator()

        CircularWavyProgressIndicator(wavelength = 20.dp, gapSize = 8.dp)

        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

        LinearWavyProgressIndicator(modifier = Modifier.fillMaxWidth())

    }
}