package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droiddevtips.multiscreensupport.domain.navigateToView
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.MainRoute
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.article.data.Article
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.articleDetail.ArticleDetailView
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
            Log.i("TAG34","Article title: ${article?.title}")
            ArticleDetailView()
        }
    }
}