@file:OptIn(ExperimentalMaterial3Api::class)

package com.droiddevtips.spotlight

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.droiddevtips.spotlight.spotlightFeature.Spotlight
import com.droiddevtips.spotlight.spotlightFeature.SpotlightInfo
import com.droiddevtips.spotlight.spotlightFeature.SpotlightType
import com.droiddevtips.typography.DroidDevTipsTheme
import com.droiddevtips.typography.extensions.clickableWithPrimaryColorRipple

class SpotlightMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar()
                    }
                ) { innerPadding ->
                    NewsScreen(
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
private fun TopBar() {

    val context = LocalContext.current

    TopAppBar(title = {
        Text("News")
    }, actions = {

        IconButton(onClick = {
            Toast.makeText(context, "Search icon clicked", Toast.LENGTH_SHORT).show()
        }) {
            Icon(
                painter = painterResource(R.drawable.search_icon),
                contentDescription = null
            )
        }

        IconButton(onClick = {
            Toast.makeText(context, "Refresh icon clicked", Toast.LENGTH_SHORT).show()
        }) {
            Icon(
                painter = painterResource(R.drawable.refresh_icon),
                contentDescription = null
            )
        }
    }, modifier = Modifier.shadow(elevation = 8.dp))
}

@Composable
private fun NewsScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(50) {
            ListItemView(item = it) {
                Toast.makeText(
                    context,
                    "Item $it clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

@Composable
fun ListItemView(
    item: Int,
    modifier: Modifier = Modifier,
    onClicked: () -> Unit
) {

    Card(onClick = {

    }) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
                .clickableWithPrimaryColorRipple(onClick = onClicked),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {

            Image(
                painter = painterResource(R.drawable.news_icon),
                contentDescription = null,
                modifier = Modifier.size(50.dp).clip(shape = RoundedCornerShape(8.dp))
            )

            Text(text = "News list item $item")

        }
    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    val showSpotlightInfo = remember { mutableStateOf<SpotlightInfo?>(null) }
    val showSpotlight = remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
    ) {  // .statusBarsPadding().navigationBarsPadding()

        //    val scope = rememberCoroutineScope()
        // .padding(top = 18.dp)
        Text(
            text = "Hello $name!",
            modifier = modifier
                .align(alignment = Alignment.BottomEnd) // .padding(top = 20.dp) // .padding(top = 18.dp)
                .onSizeChanged {
                    Log.i("TAG78", "")
                    Log.i("TAG78", "${it.width} - ${it.height}")
                    Log.i("TAG78", "")
                }
                .onGloballyPositioned {
                    showSpotlightInfo.value =
                        SpotlightInfo(
                            bounds = it.boundsInRoot(),
                            text = "Most Space Area Most Space Area Most Space Area Most Space Area 123",
                            type = SpotlightType.Circle()
                        )
                }
        )



        Button(
            onClick = {
                showSpotlight.value = true
            },
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .navigationBarsPadding(),
            enabled = showSpotlightInfo.value != null
        ) {
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

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    DroidDevTipsExamplesTheme {
//        Greeting("Android")
//    }
//}