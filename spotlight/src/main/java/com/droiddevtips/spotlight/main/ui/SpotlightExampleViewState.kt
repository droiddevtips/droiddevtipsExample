package com.droiddevtips.spotlight.main.ui

import android.os.Parcelable
import com.droiddevtips.spotlight.main.data.NewsItem
import kotlinx.parcelize.Parcelize

/**
 * The spotlight example view state data model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Parcelize
data class SpotlightExampleViewState(
    val newsItems: List<NewsItem> = emptyList(),
    val activeNewsItem: NewsItem? = null
): Parcelable