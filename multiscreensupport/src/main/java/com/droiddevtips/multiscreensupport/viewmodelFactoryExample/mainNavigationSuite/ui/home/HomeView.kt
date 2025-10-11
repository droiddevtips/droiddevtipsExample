package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ViewType
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.CustomListDetailView

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    navigate: (ListDetailItem) -> Unit
) {
    CustomListDetailView(
        viewType = ViewType.Home,
        modifier = modifier,
        navigate = navigate
    )
}