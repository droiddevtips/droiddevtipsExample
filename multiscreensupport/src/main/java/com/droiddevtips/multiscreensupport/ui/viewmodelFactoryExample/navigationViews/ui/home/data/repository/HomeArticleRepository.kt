package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.data.repository

import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.article.data.Article

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface HomeArticleRepository {

    suspend fun loadHomeArticles(): List<Article>

}