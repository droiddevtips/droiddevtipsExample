package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.repository

import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.listDetailItem.data.ViewType

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface ListDetailDemoRepository {

    suspend fun loadData(viewType: ViewType): List<ListDetailItem>

}