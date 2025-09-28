package com.droiddevtips.multiscreensupport.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droiddevtips.multiscreensupport.data.MultiScreenSupportRoute
import com.droiddevtips.multiscreensupport.domain.navigateToView
import com.droiddevtips.multiscreensupport.ui.rememberExample.RememberExample
import com.droiddevtips.multiscreensupport.ui.rememberSaveableExample.RememberSaveableExample
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ViewModelFactoryExample
import com.droiddevtips.typography.DroidDevTipsTheme

class MultiScreenExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navHostController = rememberNavController()

            DroidDevTipsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(
                        navController = navHostController,
                        startDestination = MultiScreenSupportRoute.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable(MultiScreenSupportRoute.Home.route) {
                            Column(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(
                                    space = 30.dp,
                                    alignment = Alignment.CenterVertically
                                )
                            ) {

                                Button(onClick = {
                                    navHostController.navigateToView(MultiScreenSupportRoute.RememberExample.route)
                                }) {
                                    Text("Remember example")
                                }

                                Button(onClick = {
                                    navHostController.navigateToView(MultiScreenSupportRoute.RememberSaveableExample.route)
                                }) {
                                    Text("Remember saveable example")
                                }

                                Button(onClick = {
                                    navHostController.navigateToView(MultiScreenSupportRoute.ViewModelFactoryExample.route)
                                }) {
                                    Text("Viewmodel factory example")
                                }
                            }
                        }

                        composable(MultiScreenSupportRoute.RememberExample.route) {
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

/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val number = rememberSaveable { mutableIntStateOf(1) }
    val multiScreenViewModel: MultiScreenViewModel = viewModel(
        factory =
            MultiScreenViewModelFactory()
    )


    val counter = multiScreenViewModel.counter.collectAsStateWithLifecycle()

    val width = when (windowSizeClass.windowWidthSizeClass) {

        WindowWidthSizeClass.COMPACT -> WindowWidthSizeClass.COMPACT.toString()
        WindowWidthSizeClass.MEDIUM -> WindowWidthSizeClass.MEDIUM.toString()
        WindowWidthSizeClass.EXPANDED -> WindowWidthSizeClass.EXPANDED.toString()
        else -> "Unknown width"
    }

    val height = when (windowSizeClass.windowHeightSizeClass) {

        WindowHeightSizeClass.COMPACT -> WindowHeightSizeClass.COMPACT.toString()
        WindowHeightSizeClass.MEDIUM -> WindowHeightSizeClass.MEDIUM.toString()
        WindowHeightSizeClass.EXPANDED -> WindowHeightSizeClass.EXPANDED.toString()
        else -> "Unknown height"
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "$width\n$height",
            modifier = modifier
        )

        Text(
            text = "${number.intValue}"
        )

        Text(
            text = "VM counter ${counter.value}"
        )

        Button(onClick = {
            number.intValue = ++number.intValue
        }) {
            Text("Increase number")
        }

        Button(onClick = {
            multiScreenViewModel.performAction(MultiScreenSupportAction.Increase)
        }) {
            Text("Increase number VM")
        }

    }


}
*/