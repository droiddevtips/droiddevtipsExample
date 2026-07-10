package com.droiddevtips.visibilitychanged.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droiddevtips.typography.DroidDevTipsTheme
import com.droiddevtips.visibilitychanged.data.TabRoute

class VisibilityChangedMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {

                val tabs = listOf(
                    TabRoute.List,
                    TabRoute.Pager
                )
                val selectedTab = rememberSaveable { mutableIntStateOf(0) }
                val navController = rememberNavController()
                val visibleTab = rememberSaveable { mutableStateOf(TabRoute.List.title) }

                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopBarText(
                        visibleTabText = visibleTab.value,
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                    )
                }, bottomBar = {
                    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                        tabs.forEachIndexed { index, tabItem ->
                            CustomNavigationBarItem(
                                tabItem = tabItem,
                                index = index,
                                selectedTab = selectedTab.intValue
                            ) {
                                selectedTab.intValue = index
                                navController.navigate(tabItem.route)
                            }
                        }
                    }
                }) { innerPadding ->

                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = TabRoute.List.route
                    ) {
                        composable(TabRoute.List.route) {
                            ListExample(modifier = Modifier.fillMaxSize()) {
                                visibleTab.value = it
                            }
                        }

                        composable(TabRoute.Pager.route) {
                            PagerExample(modifier = Modifier.fillMaxSize()) { title ->
                                visibleTab.value = title
                            }
                        }
                    }
                }
            }
        }
    }
}