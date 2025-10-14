package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.repository

import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItemDataSource

/**
 * The list detail demo repository implementation class, a sub class of [ListDetailDemoRepository].
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class ListDetailDemoRepositoryImp(
    private val dataSource: ListDetailItemDataSource,
) : ListDetailDemoRepository
{
    override suspend fun loadData(): List<ListDetailItem> {
        return dataSource.loadData()
    }
}