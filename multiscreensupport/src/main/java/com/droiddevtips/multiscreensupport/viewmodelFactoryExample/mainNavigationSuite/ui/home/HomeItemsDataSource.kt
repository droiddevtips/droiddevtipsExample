package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.home

import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.listDetailItem.data.ListDetailItem

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface HomeItemsDataSource {

    suspend fun loadHomeItems(): List<ListDetailItem>

}