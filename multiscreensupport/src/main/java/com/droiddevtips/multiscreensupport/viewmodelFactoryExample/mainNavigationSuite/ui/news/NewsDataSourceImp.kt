package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.news

import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.listDetailItem.data.ViewType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class NewsDataSourceImp(private val viewType: ViewType): NewsDataSource {

    override suspend fun loadNewsItems(): List<ListDetailItem> = withContext(Dispatchers.Default) {

        delay(300) // Simulate remote api call

        val itemList = ArrayList<ListDetailItem>()

        for (item in 1..50) {
//            itemList.add(item.createListDetailItem(viewType = viewType))
        }

        itemList.toList()
    }

}