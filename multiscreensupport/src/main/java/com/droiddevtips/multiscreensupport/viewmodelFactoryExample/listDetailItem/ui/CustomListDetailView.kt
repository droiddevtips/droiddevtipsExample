package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ViewType
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.repository.ListDetailDemoRepositoryImp
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.domain.parseEvent
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.grid.GridView
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.landscapeTablet.ListDetailTabletLandscapeView
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.list.ListView
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.viewmodel.ListDetailViewModel
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.viewmodel.ListDetailViewModelFactory
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.util.Device
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.util.DeviceOrientation
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.util.currentWindowSize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun CustomListDetailView(
    viewType: ViewType,
    modifier: Modifier = Modifier,
    navigate: (ListDetailItem) -> Unit
) {

    val listDetailViewModel: ListDetailViewModel = viewModel(
        factory = ListDetailViewModelFactory(
            viewType = viewType,
            repository = ListDetailDemoRepositoryImp()
        )
    )

    val viewState = listDetailViewModel.listDetailViewState.collectAsStateWithLifecycle()

    val currentWindowSize = currentWindowSize()

    if (currentWindowSize.device == Device.Mobile) {

        when (currentWindowSize.orientation) {

            DeviceOrientation.Landscape -> GridView(
                viewState = viewState,
                modifier = modifier,
                event = {
                    it.parseEvent(
                        updateViewModel = listDetailViewModel::handleEvent,
                        navigate = navigate
                    )
                })

            else -> ListView(
                viewState = viewState,
                modifier = modifier,
                event = {
                it.parseEvent(
                    updateViewModel = listDetailViewModel::handleEvent,
                    navigate = navigate
                )
            })
        }

    } else {

        when (currentWindowSize.orientation) {

            DeviceOrientation.Landscape -> ListDetailTabletLandscapeView(
                viewState = viewState,
                modifier = modifier,
                event = {
                    it.parseEvent(
                        updateViewModel = listDetailViewModel::handleEvent,
                        navigate = navigate
                    )
                })

            else -> GridView(
                viewState = viewState,
                modifier = modifier,
                event = {
                    it.parseEvent(
                        updateViewModel = listDetailViewModel::handleEvent,
                        navigate = navigate
                    )
                })
        }
    }
}