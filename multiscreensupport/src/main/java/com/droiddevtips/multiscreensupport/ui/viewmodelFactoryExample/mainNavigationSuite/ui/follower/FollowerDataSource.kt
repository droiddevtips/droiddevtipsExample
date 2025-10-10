package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.mainNavigationSuite.ui.follower

import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.listDetailItem.data.ListDetailItem

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface FollowerDataSource {

    suspend fun loadFollowers(): List<ListDetailItem>
}