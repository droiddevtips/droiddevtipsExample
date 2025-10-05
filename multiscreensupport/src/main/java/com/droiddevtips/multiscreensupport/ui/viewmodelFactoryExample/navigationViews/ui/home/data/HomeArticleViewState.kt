package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.data

import android.os.Parcelable
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.article.data.Article
import kotlinx.parcelize.Parcelize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class HomeArticleViewState(
    val articles: List<Article> = emptyList(),
    val selectedArticle: Article? = null,
    val showLoadingView: Boolean = true,
    val visibleIndex: Int = 0
) : Parcelable
