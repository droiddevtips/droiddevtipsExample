package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.ui2

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailItem
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ViewType
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.repository.ListDetailDemoRepositoryImp
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.domain.parseEvent
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.ui2.grid.GridView
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.ui2.landscapeTablet.ListDetailTabletLandscapeView
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.ui2.list.ListView
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.ui2.viewmodel.ListDetailViewModel
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.ui2.viewmodel.ListDetailViewModelFactory
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.util.Device
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.util.DeviceOrientation
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.util.currentWindowSize

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

            else -> ListView(viewState = viewState, modifier = modifier, event = {
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