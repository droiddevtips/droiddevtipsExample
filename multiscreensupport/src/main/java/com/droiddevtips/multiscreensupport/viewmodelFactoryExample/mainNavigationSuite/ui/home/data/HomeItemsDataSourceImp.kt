package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.home.data

import com.droiddevtips.multiscreensupport.common.extensions.toListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItemDataSource
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ViewType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * The Home items data source implementation which implement the [ListDetailItemDataSource] interface.
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class HomeItemsDataSourceImp: ListDetailItemDataSource {

    override suspend fun loadData(): List<ListDetailItem> = withContext(Dispatchers.Default) {
        delay(300) // Simulate remote api call

        val itemList = ArrayList<ListDetailItem>()

        for (item in 1..50) {
            item.toListDetailItem(viewType = ViewType.Home)?.let {
                itemList.add(it)
            }
        }

        itemList.toList()
    }
}