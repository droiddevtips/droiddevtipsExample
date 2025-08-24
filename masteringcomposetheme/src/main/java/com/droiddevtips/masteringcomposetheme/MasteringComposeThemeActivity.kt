package com.droiddevtips.masteringcomposetheme

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.droiddevtips.masteringcomposetheme.feature.checkbox.ui.CheckBoxExample
import com.droiddevtips.masteringcomposetheme.feature.textfield.ui.OutlineTextFieldErrorExample
import com.droiddevtips.masteringcomposetheme.feature.textfield.ui.OutlineTextFieldExample
import com.droiddevtips.masteringcomposetheme.feature.textfield.ui.PasswordTextFieldExample
import com.droiddevtips.masteringcomposetheme.feature.textfield.ui.TextFieldCustomLeadingIconColorExample
import com.droiddevtips.masteringcomposetheme.feature.textfield.ui.TextFieldErrorStateExample
import com.droiddevtips.masteringcomposetheme.feature.textfield.ui.TextFieldExample
import com.droiddevtips.masteringcomposetheme.feature.textfield.ui.TextFieldInDisableStateExample
import com.droiddevtips.masteringcomposetheme.feature.textfield.ui.TextFieldWithPrefixExample
import com.droiddevtips.masteringcomposetheme.feature.textfield.ui.TextFieldWithSuffixExample
import com.droiddevtips.typography.DroidDevTipsTheme

class MasteringComposeThemeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MasteringThemeMainContainer(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun MasteringThemeMainContainer(modifier: Modifier = Modifier) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Card(modifier = Modifier.fillMaxWidth()) {

            Column(
                modifier = Modifier.padding(all = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Buttons",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

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
                    Text("Text button")
                }
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {

            Column(
                modifier = Modifier.padding(all = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "TextField",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                TextFieldExample()

                TextFieldCustomLeadingIconColorExample()

                TextFieldErrorStateExample()

                TextFieldInDisableStateExample()

                TextFieldWithSuffixExample()

                TextFieldWithPrefixExample()

                PasswordTextFieldExample()

                OutlineTextFieldExample()

                OutlineTextFieldErrorExample()
            }
        }

        CheckBoxExample()

        Spacer(modifier = Modifier.height(32.dp))
    }
}

private fun demoToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}