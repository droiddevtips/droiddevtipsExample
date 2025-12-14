package com.droiddevtips.floatingtabbarandpip.common.videoList.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Teh video detail data model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Serializable
data class VideoDetails(
    @SerialName("title")
    val title: String? = "-",
    @SerialName("author_name")
    val authorName: String? = "-",
    @SerialName("author_url")
    val authorUrl: String? = "-"
)