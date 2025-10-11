package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.main

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droiddevtips.multiscreensupport.common.extensions.navigateToView
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.detail.DetailView
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.data.MainRoute
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.MainNavigationSuiteScaffold

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

            MainNavigationSuiteScaffold(navigate = { item ->
                mainNavController.currentBackStackEntry?.savedStateHandle?.set("item", item)
                mainNavController.navigateToView(route = MainRoute.Detail.route)
            })
        }

        composable(route = MainRoute.Detail.route) {

            val article = mainNavController.previousBackStackEntry?.savedStateHandle?.get<ListDetailItem>("item")
            Log.i("TAG34","Article title: ${article?.title}")
            DetailView()
        }
    }
}