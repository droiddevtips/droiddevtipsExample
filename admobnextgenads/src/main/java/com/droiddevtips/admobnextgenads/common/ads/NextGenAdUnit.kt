package com.droiddevtips.admobnextgenads.common.ads

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The next gen ad units
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
sealed class NextGenAdUnit(val id: String, val contentUrl: String, val key: String): Parcelable {

    data class BannerAd(val _key:String = ""): NextGenAdUnit(id = "ca-app-pub-3940256099942544/9214589741", contentUrl = "www.droiddevtips.com", key = _key)
    data object AnchoredBannerAd: NextGenAdUnit(id = "ca-app-pub-3940256099942544/9214589741", contentUrl = "www.droiddevtips.com", key = "")
    data object CollapsibleBannerAd: NextGenAdUnit(id = "ca-app-pub-3940256099942544/9214589741", contentUrl = "www.droiddevtips.com", key = "")
    data object InterstitialAd: NextGenAdUnit(id = "ca-app-pub-3940256099942544/1033173712", contentUrl = "www.droiddevtips.com", key = "")
    data object NativeAdVideo: NextGenAdUnit(id = "ca-app-pub-3940256099942544/1044960115", contentUrl = "www.droiddevtips.com", key = "")
    data object NativeAdImage: NextGenAdUnit(id = "ca-app-pub-3940256099942544/2247696110", contentUrl = "www.droiddevtips.com", key = "")
    data object NativeAdFullscreen: NextGenAdUnit(id = "ca-app-pub-3940256099942544/2247696110", contentUrl = "www.droiddevtips.com", key = "")
    data object RewardedAd: NextGenAdUnit(id = "ca-app-pub-3940256099942544/5224354917", contentUrl = "www.droiddevtips.com", key = "")
    data object RewardedInterstitialAd: NextGenAdUnit(id = "ca-app-pub-3940256099942544/5354046379", contentUrl = "www.droiddevtips.com", key = "")

}