package com.droiddevtips.admobnextgenads.common.ads

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
sealed class NextGenAdUnit(val id: String, val contentUrl: String, val key: String): Parcelable {

    data class BannerAd(val _key:String = ""): NextGenAdUnit(id = "ca-app-pub-3940256099942544/9214589741", contentUrl = "www.droiddevtips.com", key = _key)
    //data class BannerAd(val _key:String = ""): NextGenAdUnit(id = "", contentUrl = "www.droiddevtips.com", key = _key)

}