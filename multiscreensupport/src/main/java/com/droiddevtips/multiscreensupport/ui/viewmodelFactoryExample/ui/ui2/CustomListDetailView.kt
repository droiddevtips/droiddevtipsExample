package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.ui2

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.repository.ListDetailDemoRepositoryImp
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailItem
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ViewType
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

    val listDetailViewModel: ListDetailViewModel = viewModel(factory = ListDetailViewModelFactory(
        viewType = viewType,
        repository = ListDetailDemoRepositoryImp()
    )
    )

    val viewState = listDetailViewModel.listDetailViewState.collectAsStateWithLifecycle()

    val currentWindowSize = currentWindowSize()

    if (currentWindowSize.device == Device.Mobile) {

        when(currentWindowSize.orientation) {

            DeviceOrientation.Landscape -> {}

            else -> {

            }
        }

    } else {

        when(currentWindowSize.orientation) {

            DeviceOrientation.Landscape -> {}

            else -> {

            }
        }

    }

}