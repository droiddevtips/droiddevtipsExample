package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.repository

import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailItem
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ViewType

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface ListDetailDemoRepository {

    suspend fun loadData(viewType: ViewType): List<ListDetailItem>

}