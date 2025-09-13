package com.droiddevtips.masteringcomposetheme.feature.button.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.droiddevtips.masteringcomposetheme.common.MasteringThemeString
import com.droiddevtips.masteringcomposetheme.feature.customCard.ui.AppCustomCard

/**
 * This is the material 3 buttons example in jetpack compose
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun Material3ButtonExample(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    AppCustomCard(modifier = modifier, title = stringResource(id = MasteringThemeString.buttons)) {

        Button(
            onClick = {
                demoToast(context = context, "Filled button clicked!")
            },
            shape = RoundedCornerShape(24)
        ) {
            Text(text = stringResource(id = MasteringThemeString.filled_buttons))
        }

        Button(
            enabled = false, onClick = {
                demoToast(context = context, "Filled button clicked!")
            },
            shape = RoundedCornerShape(24)
        ) {
            Text(text = stringResource(id = MasteringThemeString.filled_buttons_disabled))
        }

        FilledTonalButton(onClick = {
            demoToast(context = context, "Tonal button clicked!")
        }) {
            Text(text = stringResource(id = MasteringThemeString.tonal_button))
        }

        FilledTonalButton(enabled = false, onClick = {
            demoToast(context = context, "Tonal button clicked!")
        }) {
            Text(text = stringResource(id = MasteringThemeString.tonal_button_disabled))
        }

        OutlinedButton(onClick = {
            demoToast(context = context, "Outlined button clicked!")
        }) {
            Text(text = stringResource(id = MasteringThemeString.outlined_button))
        }

        OutlinedButton(enabled = false, onClick = {
            demoToast(context = context, "Outlined button clicked!")
        }) {
            Text(text = stringResource(id = MasteringThemeString.outlined_button_disabled))
        }

        ElevatedButton(onClick = {
            demoToast(context = context, "Elevated button clicked!")
        }) {
            Text(text = stringResource(id = MasteringThemeString.elevated_button))
        }

        ElevatedButton(enabled = false, onClick = {
            demoToast(context = context, "Elevated button clicked!")
        }) {
            Text(text = stringResource(id = MasteringThemeString.elevated_button_disabled))
        }

        TextButton(onClick = {
            demoToast(context = context, "Text button clicked!")
        }) {
            Text(text = stringResource(id = MasteringThemeString.text_button))
        }

        TextButton(enabled = false, onClick = {
            demoToast(context = context, "Text button clicked!")
        }) {
            Text(text = stringResource(id = MasteringThemeString.text_button_disabled))
        }

    }
}

private fun demoToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}