package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droiddevtips.multiscreensupport.common.extensions.navigateToView
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.data.MainNestedNavRoute
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.data.TabItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.follower.ui.FollowerView
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.home.ui.HomeView
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.news.ui.NewsView
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.util.Device
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.util.currentWindowSize
import kotlinx.coroutines.launch

/**
 * The main navigation suite scaffold
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun MainNavigationSuiteScaffold(modifier: Modifier = Modifier, navigate: (ListDetailItem) -> Unit) {

    val currentWindowSize = currentWindowSize()

    val scope = rememberCoroutineScope()
    var selectedDestination by rememberSaveable { mutableIntStateOf(0) }

    val tabs = listOf(
        TabItem.Home,
        TabItem.News,
        TabItem.Follower
    )

    val nestedNavController = rememberNavController()

    NavigationSuiteScaffold(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = if (isSystemInDarkTheme()) Color(0xFF1D1D1D) else MaterialTheme.colorScheme.secondaryContainer,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationRailContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            navigationRailContentColor = Color.Cyan,
            navigationBarContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            navigationBarContentColor = MaterialTheme.colorScheme.surfaceContainer,
            shortNavigationBarContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            shortNavigationBarContentColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        navigationSuiteItems = {

            tabs.forEachIndexed { index, tabItem ->

                this.item(selected = index == selectedDestination, icon = {
                    TabIcon(
                        selected = index == selectedDestination,
                        icon = tabItem.icon
                    )
                }, label = {
                    TabTitle(
                        selected = index == selectedDestination,
                        text = tabItem.titleRes
                    )
                }, onClick = {
                    scope.launch {
                        selectedDestination = index
                        nestedNavController.navigateToView(route = tabItem.route)
                    }
                })
            }

        },
        content = {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surfaceContainer) // Color(0xFFF8F8F8) MaterialTheme.colorScheme.surfaceContainer
            ) {

                Box(modifier = Modifier
                    .then(
                        if (currentWindowSize.device == Device.Tablet) {
                            Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .fillMaxSize(fraction = 0.99f)
                        } else {
                            Modifier.fillMaxSize()
                        }
                    ).background(color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surface else Color(0xFFE1E1E1)) // MaterialTheme.colorScheme.surface
                ) {

                    NavHost(nestedNavController, TabItem.Home.route) {

                        composable(route = TabItem.Home.route) {
//                            CustomListDetailView(viewType = ViewType.Home, navigate = navigate)
                            HomeView(navigate = navigate)
                        }

                        composable(route = TabItem.News.route) {
                            NewsView(navigate = navigate)
                        }

                        composable(route = TabItem.Follower.route) {
                            FollowerView(navigate = navigate)

//                            Column(
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .background(color = Color.LightGray)
//                            ) {
//                                Text("Follower")
//                            }
                        }

                        composable(route = MainNestedNavRoute.ArticleDetail.route) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = Color.DarkGray)
                            ) {
                                Text("Article detail")
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun TabIcon(
    modifier: Modifier = Modifier,
    selected: Boolean,
    icon: Int,
    contentDescription: String? = null
) {
    Icon(
        modifier = modifier.size(24.dp),
        painter = painterResource(icon),
        contentDescription = contentDescription,
        tint = if (selected) MaterialTheme.colorScheme.primary else if (isSystemInDarkTheme()) Color.White else Color.Black
    )
}

@Composable
private fun TabTitle(
    modifier: Modifier = Modifier,
    selected: Boolean,
    text: Int
) {
    Text(
        modifier = modifier,
        text = stringResource(id = text),
        color = if (selected) MaterialTheme.colorScheme.primary else if (isSystemInDarkTheme()) Color.White else Color.Black
    )
}