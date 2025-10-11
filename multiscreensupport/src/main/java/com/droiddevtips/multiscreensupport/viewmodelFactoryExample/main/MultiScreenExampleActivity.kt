package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droiddevtips.multiscreensupport.common.data.AppString
import com.droiddevtips.multiscreensupport.common.data.MultiScreenSupportRoute
import com.droiddevtips.multiscreensupport.common.extensions.navigateToView
import com.droiddevtips.multiscreensupport.rememberExample.RememberExample
import com.droiddevtips.multiscreensupport.rememberSaveableExample.RememberSaveableExample
import com.droiddevtips.typography.DroidDevTipsTheme

/**
 * The main activity for the multi screen example.
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class MultiScreenExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navHostController = rememberNavController()

            DroidDevTipsTheme {
                Surface(color = MaterialTheme.colorScheme.primaryContainer) {
                    Column(
                        modifier = Modifier.Companion
                            .fillMaxSize()
                    ) {

                        NavHost(
                            navController = navHostController,
                            startDestination = MultiScreenSupportRoute.Home.route
                        ) {

                            composable(route = MultiScreenSupportRoute.Home.route) {
                                Column(
                                    modifier = Modifier.Companion

                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.Companion.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(
                                        space = 30.dp,
                                        alignment = Alignment.Companion.CenterVertically
                                    )
                                ) {

                                    Button(onClick = {
                                        navHostController.navigateToView(MultiScreenSupportRoute.RememberExample.route)
                                    }) {
                                        Text(text = stringResource(id = AppString.remember_example))
                                    }

                                    Button(onClick = {
                                        navHostController.navigateToView(MultiScreenSupportRoute.RememberSaveableExample.route)
                                    }) {
                                        Text(text = stringResource(id = AppString.remember_saveable_example))
                                    }

                                    Button(onClick = {
                                        navHostController.navigateToView(MultiScreenSupportRoute.ViewModelFactoryExample.route)
                                    }) {
                                        Text(text = stringResource(id = AppString.viewmodel_factory_example))
                                    }
                                }
                            }

                            composable(route = MultiScreenSupportRoute.RememberExample.route) {
                                RememberExample()
                            }

                            composable(route = MultiScreenSupportRoute.RememberSaveableExample.route) {
                                RememberSaveableExample()
                            }

                            composable(route = MultiScreenSupportRoute.ViewModelFactoryExample.route) {
                                ViewModelFactoryExample()
                            }
                        }
                    }
                }
            }
        }
    }
}