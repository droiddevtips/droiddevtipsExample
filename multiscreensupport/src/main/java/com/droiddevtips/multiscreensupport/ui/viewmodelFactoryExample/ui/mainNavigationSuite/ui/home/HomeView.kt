package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.mainNavigationSuite.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.article.data.Article
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.mainNavigationSuite.ui.home.data.repository.HomeArticleRepositoryImpl
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.mainNavigationSuite.ui.home.domain.parseEvent
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.mainNavigationSuite.ui.home.viewmodel.HomeArticleViewModel
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.mainNavigationSuite.ui.home.viewmodel.HomeViewModelFactory
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.util.Device
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.util.DeviceOrientation
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.util.currentWindowSize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun HomeView(modifier: Modifier = Modifier, navigate: (Article) -> Unit) {

    val homeArticleViewModel: HomeArticleViewModel =
        viewModel(factory = HomeViewModelFactory(repository = HomeArticleRepositoryImpl()))

    val viewState = homeArticleViewModel.homeArticleViewState.collectAsStateWithLifecycle()

    val currentWindowSize = currentWindowSize()

    if (currentWindowSize.device == Device.Mobile) {

        when (currentWindowSize.orientation) {

            DeviceOrientation.Landscape -> HomeMobileLandscapeView(
                viewState = viewState,
                modifier = modifier,
                event = {
                    it.parseEvent(
                        updateViewModel = homeArticleViewModel::handleEvent,
                        navigate = navigate
                    )
                }
            )

            else -> HomeMobilePortraitView(
                viewState = viewState,
                modifier = Modifier.fillMaxSize(),
                event = {
                    it.parseEvent(
                        updateViewModel = homeArticleViewModel::handleEvent,
                        navigate = navigate
                    )
                }
            )
        }
    } else {

        when (currentWindowSize.orientation) {

            DeviceOrientation.Landscape -> HomeTabletLandscapeView(
                viewState = viewState,
                modifier = modifier,
                event = {
                    it.parseEvent(
                        updateViewModel = homeArticleViewModel::handleEvent,
                        navigate = navigate
                    )
                }
            )

            else -> HomeTabletPortraitView(
                viewState = viewState,
                modifier = modifier,
                event = {
                    it.parseEvent(
                        updateViewModel = homeArticleViewModel::handleEvent,
                        navigate = navigate
                    )
                }
            )
        }
    }
}