package com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui.listView.data

import android.os.Parcelable
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import kotlinx.parcelize.Parcelize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class ListItem(val title:String, val adUnit: NextGenAdUnit? = null): Parcelable
