package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.follower.data

import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem

/**
 * Follower data source interface
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface FollowerDataSource {
    suspend fun loadFollowers(): List<ListDetailItem>
}