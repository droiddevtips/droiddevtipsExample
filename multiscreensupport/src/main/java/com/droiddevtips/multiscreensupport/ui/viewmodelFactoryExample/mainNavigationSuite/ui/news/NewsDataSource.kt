package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.mainNavigationSuite.ui.news

import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.listDetailItem.data.ListDetailItem

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface NewsDataSource {

    suspend fun loadNewsItems(): List<ListDetailItem>

}