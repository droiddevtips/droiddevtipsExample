package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.repository

import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ViewType
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.follower.data.FollowerDataSource
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.follower.data.FollowerDataSourceImp
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.home.data.HomeItemsDataSource
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.home.data.HomeItemsDataSourceImp
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.news.data.NewsDataSource
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.mainNavigationSuite.ui.news.data.NewsDataSourceImp

/**
 * The list detail demo repository implementation class, a sub class of [ListDetailDemoRepository].
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