package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.news.data

import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem

/**
 * The news data source interface
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface NewsDataSource {
    suspend fun loadNewsItems(): List<ListDetailItem>

}