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
import com.droiddevtips.masteringcomposetheme.feature.customCard.ui.AppCustomCard

/**
 * This is the material 3 buttons example in jetpack compose
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun Material3ButtonExample(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    AppCustomCard(modifier = modifier, title = "Buttons") {

        Button(
            onClick = {
                demoToast(context = context, "Filled button clicked!")
            },
            shape = RoundedCornerShape(24)
        ) {
            Text("Filled button")
        }

        Button(
            enabled = false, onClick = {
                demoToast(context = context, "Filled button clicked!")
            },
            shape = RoundedCornerShape(24)
        ) {
            Text("Filled button disabled")
        }

        FilledTonalButton(onClick = {
            demoToast(context = context, "Tonal button clicked!")
        }) {
            Text("Tonal button")
        }

        FilledTonalButton(enabled = false, onClick = {
            demoToast(context = context, "Tonal button clicked!")
        }) {
            Text("Tonal button (disabled)")
        }

        OutlinedButton(onClick = {
            demoToast(context = context, "Outlined button clicked!")
        }) {
            Text("Outlined button")
        }

        OutlinedButton(enabled = false, onClick = {
            demoToast(context = context, "Outlined button clicked!")
        }) {
            Text("Outlined button (disabled)")
        }

        ElevatedButton(onClick = {
            demoToast(context = context, "Elevated button clicked!")
        }) {
            Text("Elevated button")
        }

        ElevatedButton(enabled = false, onClick = {
            demoToast(context = context, "Elevated button clicked!")
        }) {
            Text("Elevated button disabled")
        }

        TextButton(onClick = {
            demoToast(context = context, "Text button clicked!")
        }) {
            Text("Text button")
        }

        TextButton(enabled = false, onClick = {
            demoToast(context = context, "Text button clicked!")
        }) {
            Text("Text button disabled")
        }

    }
}

private fun demoToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}