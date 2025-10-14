package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.repository

import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem

/**
 * The list detail demo repository interface
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface ListDetailDemoRepository {
    suspend fun loadData(): List<ListDetailItem>

}