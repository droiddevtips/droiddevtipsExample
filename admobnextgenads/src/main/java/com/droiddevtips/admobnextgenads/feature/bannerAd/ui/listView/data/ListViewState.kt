package com.droiddevtips.admobnextgenads.feature.bannerAd.ui.listView.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The list view state data model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class ListViewState(val isLoading:Boolean = true, val items: List<ListItem> = emptyList()): Parcelable
