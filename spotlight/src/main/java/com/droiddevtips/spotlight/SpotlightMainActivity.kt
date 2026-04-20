package com.droiddevtips.spotlight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droiddevtips.spotlight.spotlightFeature.Spotlight
import com.droiddevtips.spotlight.spotlightFeature.SpotlightInfo
import com.droiddevtips.spotlight.spotlightFeature.SpotlightType
import com.droiddevtips.spotlight.ui.theme.DroidDevTipsExamplesTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SpotlightMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidDevTipsExamplesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    val showSpotlightInfo = remember { mutableStateOf<SpotlightInfo?>(null) }
    val showSpotlight = remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxSize().background(color = Color.Blue)) {

        //    val scope = rememberCoroutineScope()
        Text(
            text = "Hello $name!",
            modifier = modifier
                .align(alignment = Alignment.TopEnd)
                .onGloballyPositioned {
                    showSpotlightInfo.value =
                        SpotlightInfo(bounds = it.boundsInRoot(), text = "Test", type = SpotlightType.Rect())
                }
        )



        Button(onClick = {
            showSpotlight.value = true
        }, modifier = Modifier.align(alignment = Alignment.Center).navigationBarsPadding() ,enabled = showSpotlightInfo.value != null) {
            Text(text = "Show Spotlight")
        }


//        LaunchedEffect(showSpotlightInfo.value) {
//            delay(2000)
//            if (showSpotlightInfo.value != null) {
//                showSpotlight.value = true
//            }
//        }

        if (showSpotlight.value && showSpotlightInfo.value != null) {
            Spotlight(showSpotlightInfo.value) {
                showSpotlight.value = false
            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DroidDevTipsExamplesTheme {
        Greeting("Android")
    }
}