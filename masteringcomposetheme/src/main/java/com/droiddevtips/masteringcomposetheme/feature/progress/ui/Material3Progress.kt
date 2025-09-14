@file:OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)

package com.droiddevtips.masteringcomposetheme.feature.progress.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.masteringcomposetheme.common.MasteringThemeString
import com.droiddevtips.masteringcomposetheme.feature.customCard.ui.AppCustomCard

/**
 * This is the Material 3 progress examples
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun Material3Progress(modifier: Modifier = Modifier) {

    AppCustomCard(modifier = modifier, title = stringResource(id = MasteringThemeString.progress_indicators)) {
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

        Box(modifier = Modifier.border(width = 2.dp, color = Color.LightGray)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = MasteringThemeString.determinate), color = MaterialTheme.colorScheme.onSurfaceVariant)

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val progress = remember { mutableFloatStateOf(0.0f) }
                    val sliderState =
                        rememberSliderState(
                            value = progress.floatValue,
                            valueRange = 0f..100f)

                    Column(
                        verticalArrangement = Arrangement.spacedBy(25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp),
                            progress = { (sliderState.value / 100) }
                        )

                        CircularWavyProgressIndicator(
                            modifier = Modifier.size(40.dp),
                            progress = { (sliderState.value / 100) }
                        )

                        LinearProgressIndicator(
                            progress = { (sliderState.value / 100) },
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        LinearWavyProgressIndicator(
                            progress = { (sliderState.value / 100) },
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = stringResource(id = MasteringThemeString.move_slider_text), fontSize = 10.sp)
                        Slider(
                            state = sliderState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        )
                    }
                }
            }
        }

        Box(modifier = Modifier.border(width = 2.dp, color = Color.LightGray)) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(all = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = MasteringThemeString.indeterminate_text), color = MaterialTheme.colorScheme.onSurfaceVariant)

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()

                    CircularWavyProgressIndicator(wavelength = 20.dp, gapSize = 8.dp)

                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

                    LinearWavyProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}