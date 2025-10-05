package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droiddevtips.multiscreensupport.domain.navigateToView
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.MainRoute
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.article.data.Article
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.mainNavigationSuite.ui.MainNavigationSuiteScaffold

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun ViewModelFactoryExample(modifier: Modifier = Modifier) {

    val mainNavController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = mainNavController,
        startDestination = MainRoute.Main.route
    ) {
        composable(MainRoute.Main.route) {

            MainNavigationSuiteScaffold(navigate = { article ->
                mainNavController.currentBackStackEntry?.savedStateHandle?.set("article", article)
                mainNavController.navigateToView(route = MainRoute.Detail.route)
            })
        }

        composable(route = MainRoute.Detail.route) {

            val article = mainNavController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .background(color = Color.DarkGray)
            ) {

                Text(text = "Article detail -> ${article?.title}")

            }
        }
    }
}