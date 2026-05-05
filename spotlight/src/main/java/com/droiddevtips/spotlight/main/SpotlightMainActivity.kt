@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)

package com.droiddevtips.spotlight.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.droiddevtips.spotlight.R
import com.droiddevtips.spotlight.spotlight.domain.SpotlightManager
import com.droiddevtips.spotlight.spotlight.data.SpotlightManagerAction
import com.droiddevtips.spotlight.main.SpotlightViewModelFactory
import com.droiddevtips.spotlight.extensions.observeSpotlightView
import com.droiddevtips.spotlight.spotlight.ui.Spotlight
import com.droiddevtips.spotlight.spotlight.data.SpotlightInfo
import com.droiddevtips.spotlight.spotlight.data.SpotlightType
import com.droiddevtips.typography.DroidDevTipsTheme
import kotlinx.coroutines.launch

class SpotlightMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {

                val activeSpotlight = SpotlightManager.activeSpotlight.collectAsStateWithLifecycle()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar(SpotlightManager::performAction)
                    }
                ) { innerPadding ->
                    NewsScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }

                if (activeSpotlight.value != null) {
                    Spotlight(activeSpotlight.value) {
                        SpotlightManager.performAction(action = SpotlightManagerAction.OnDismissSpotlight)
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBar(viewAction: (SpotlightManagerAction) -> Unit) {

    val context = LocalContext.current

    TopAppBar(title = {
        Text("News")
    }, actions = {

        IconButton(onClick = {
            Toast.makeText(context, "Search icon clicked", Toast.LENGTH_SHORT).show()
        }, modifier = Modifier.observeSpotlightView(id = "search_button") { id, rect ->
            viewAction(
                SpotlightManagerAction.AddSpotlightInfo(
                    SpotlightInfo(
                        id = id,
                        bounds = rect,
                        text = "The search button to search for news",
                        type = SpotlightType.Circle()
                    )
                )
            )
        }) {
            Icon(
                painter = painterResource(R.drawable.search_icon),
                contentDescription = null
            )
        }

        IconButton(onClick = {
            Toast.makeText(context, "Refresh icon clicked", Toast.LENGTH_SHORT).show()
        }, modifier = Modifier.observeSpotlightView(id = "refresh_button") { id, rect ->
            viewAction(
                SpotlightManagerAction.AddSpotlightInfo(
                    SpotlightInfo(
                        id = id,
                        bounds = rect,
                        text = "The refresh button to refresh news list",
                        type = SpotlightType.Rect()
                    )
                )
            )

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

    val spotlightExampleViewModel: SpotlightExampleViewModel =
        viewModel(factory = SpotlightViewModelFactory())
    val viewState = spotlightExampleViewModel.viewState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val navigator = rememberListDetailPaneScaffoldNavigator<NewsItem>()
    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            AnimatedPane {
                NewsListView(
                    newsList = viewState.value.newsItems,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    onClicked = { newsItem ->
                        scope.launch {
                            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, newsItem)
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                val newsItem = navigator.currentDestination?.contentKey ?: NewsItem()
                NewsDetailView(newsItem = newsItem, modifier = Modifier.fillMaxSize())
            }
        }
    )
}
