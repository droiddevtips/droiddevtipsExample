package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.article.data.Article
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.domain.Device
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.domain.DeviceOrientation
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.domain.currentWindowSize
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.data.repository.HomeArticleRepositoryImpl
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.domain.parseEvent
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.viewmodel.HomeArticleViewModel
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.viewmodel.HomeViewModelFactory

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