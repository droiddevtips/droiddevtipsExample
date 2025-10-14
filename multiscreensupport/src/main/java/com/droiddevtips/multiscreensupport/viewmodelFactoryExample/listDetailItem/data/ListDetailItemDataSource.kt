package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface ListDetailItemDataSource {
    suspend fun loadData(): List<ListDetailItem>
}