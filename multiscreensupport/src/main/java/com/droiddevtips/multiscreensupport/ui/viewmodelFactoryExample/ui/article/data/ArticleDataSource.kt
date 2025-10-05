package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.article.data


/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
interface ArticleDataSource {

    suspend fun loadArticles(): List<Article>

}