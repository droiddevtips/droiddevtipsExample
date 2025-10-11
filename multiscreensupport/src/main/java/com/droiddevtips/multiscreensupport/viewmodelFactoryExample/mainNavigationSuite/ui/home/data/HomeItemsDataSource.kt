package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.home.data

import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem

/**
 * The Home items data source interface
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface HomeItemsDataSource {

    suspend fun loadHomeItems(): List<ListDetailItem>

}