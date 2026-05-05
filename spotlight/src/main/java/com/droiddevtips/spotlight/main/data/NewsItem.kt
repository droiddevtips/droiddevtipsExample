package com.droiddevtips.spotlight.main.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.droiddevtips.spotlight.R
import kotlinx.parcelize.Parcelize

/**
 * The news item data model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Parcelize
data class NewsItem(
    @field:DrawableRes val icon: Int = R.drawable.news_icon,
    val title: String = "No title found",
    val subtitle: String = "No subtitle found"
) : Parcelable