package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.news.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ViewType
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.CustomListDetailView

/**
 * The news composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun NewsView(
    modifier: Modifier = Modifier,
    navigate: (ListDetailItem) -> Unit
) {
    CustomListDetailView(
        viewType = ViewType.News,
        modifier = modifier,
        navigate = navigate
    )
}