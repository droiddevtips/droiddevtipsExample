package com.droiddevtips.admobnextgenads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.admobnextgenads.ui.theme.DroidDevTipsExamplesTheme

class AdMobNextGenExample : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidDevTipsExamplesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val scrollable = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .verticalScroll(scrollable),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            Alignment.CenterVertically
                        )
                    ) {
                        Button(onClick = {

                        }) {
                            Text(text = "Inline Adaptive banner ad")
                        }

                        Button(onClick = {

                        }) {
                            Text("Collapsible banner ad")
                        }

                        Button(onClick = {

                        }) {
                            Text("Fixed size banner ad")
                        }

                        Button(onClick = {

                        }) {
                            Text("Interstitial ad")
                        }

                        Button(onClick = {

                        }) {
                            Text("Native ad")
                        }

                        Button(onClick = {

                        }) {
                            Text("Rewarded ad")
                        }

                        Button(onClick = {

                        }) {
                            Text("Rewarded interstitial ads")
                        }
                    }
                }
            }
        }
    }
}