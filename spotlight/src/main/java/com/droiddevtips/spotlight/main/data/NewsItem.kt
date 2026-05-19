package com.droiddevtips.spotlight.main.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.droiddevtips.spotlight.common.SpotlightResDrawable
import kotlinx.parcelize.Parcelize

/**
 * The news item data model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Parcelize
data class NewsItem(
    @field:DrawableRes val icon: Int = SpotlightResDrawable.news_icon,
    val title: String,
    val subtitle: String
) : Parcelable