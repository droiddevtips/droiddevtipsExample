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
                Text("Buttons", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)

                Button(onClick = {
                    demoToast(context = context,"Filled button clicked!")
                }) {
                    Text("Filled button")
                }

                FilledTonalButton(onClick = {
                    demoToast(context = context,"Tonal button clicked!")
                }) {
                    Text("Tonal button")
                }

                OutlinedButton(onClick = {
                    demoToast(context = context,"Tonal button clicked!")
                }) {
                    Text("Outlined button")
                }

                ElevatedButton(onClick = {}) {
                    Text("Elevated button")
                }

                TextButton(onClick = {}) {
                    Text("Text button")
                }
            }
        }



//        Text(text = "Text component")
//
//        var textFieldText by rememberSaveable { mutableStateOf("") }
//
//        TextField(
//            value = textFieldText,
//            maxLines = 1,
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
//            onValueChange = {
//                textFieldText = it
//            }, label = {
//                Text(text = "Label")
//            }
//        )

//        var outlinedTextFieldText by rememberSaveable { mutableStateOf("") }
//
//        OutlinedTextField(
//            value = outlinedTextFieldText,
//            maxLines = 1,
//            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//            onValueChange = {
//                outlinedTextFieldText = it
//            }, label = {
//                Text(text = "Label")
//            }
//        )


//        for (nums in 1..50) {
//            Button(onClick = {}) {
//                Text("Button $nums")
//            }
//        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

private fun demoToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}