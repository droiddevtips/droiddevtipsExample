package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.follower.data

import com.droiddevtips.multiscreensupport.common.extensions.toListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ViewType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Follower data source implementation, a subclass of [FollowerDataSource]
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class FollowerDataSourceImp(private val viewType: ViewType): FollowerDataSource {

    override suspend fun loadFollowers(): List<ListDetailItem> = withContext(Dispatchers.Default) {

        delay(300) // Simulate remote api call

        val itemList = ArrayList<ListDetailItem>()

        for (item in 1..50) {
            itemList.add(item.toListDetailItem(viewType = viewType))
        }

        itemList.toList()
    }

}