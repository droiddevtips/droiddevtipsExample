package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.repository

import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailItem
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ViewType
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.datasource.follower.FollowerDataSource
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.datasource.follower.FollowerDataSourceImp
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.datasource.home.HomeItemsDataSource
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.datasource.home.HomeItemsDataSourceImp
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.datasource.news.NewsDataSource
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.datasource.news.NewsDataSourceImp

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class ListDetailDemoRepositoryImp(
    private val homeDataSource: HomeItemsDataSource = HomeItemsDataSourceImp(viewType = ViewType.Home),
    private val newsDataSource: NewsDataSource = NewsDataSourceImp(viewType = ViewType.News),
    private val followerDataSource: FollowerDataSource = FollowerDataSourceImp(viewType = ViewType.Follower)
) : ListDetailDemoRepository
{

    override suspend fun loadData(viewType: ViewType): List<ListDetailItem> {

        return when(viewType) {

            ViewType.Home -> homeDataSource.loadHomeItems()

            ViewType.News -> newsDataSource.loadNewsItems()

            ViewType.Follower -> followerDataSource.loadFollowers()
        }
    }
}