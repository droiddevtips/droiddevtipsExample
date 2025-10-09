package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.datasource.news

import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailItem
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ViewType
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.domain.createListDetailItem
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
            itemList.add(item.createListDetailItem(viewType = viewType))
        }

        itemList.toList()
    }

}