package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.data.repository

import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.article.data.Article
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.article.data.ArticleDataSourceImp

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class HomeArticleRepositoryImpl: HomeArticleRepository {

    private val articleDataSource = ArticleDataSourceImp()

    override suspend fun loadHomeArticles(): List<Article> {
        return articleDataSource.loadArticles()
    }
}