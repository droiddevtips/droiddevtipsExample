package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.datasource.home

import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailItem

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface HomeItemsDataSource {

    suspend fun loadHomeItems(): List<ListDetailItem>

}