package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.datasource.news

import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailItem

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface NewsDataSource {

    suspend fun loadNewsItems(): List<ListDetailItem>

}