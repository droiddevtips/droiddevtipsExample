package com.droiddevtips.floatingtabbarandpip.common.videoRepository

import com.droiddevtips.floatingtabbarandpip.common.videoItem.VideoItem
import com.droiddevtips.floatingtabbarandpip.extensions.asVideoItem

/**
 * The videos repository implementation
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
object VideoRepositoryImpl: VideoRepository {

    private var favoriteVideosList: List<VideoItem> = emptyList()
    private var videosList: List<VideoItem> = emptyList()

    override fun getVideos(): List<VideoItem> {

        if (videosList.isNotEmpty())
            return videosList

        this.videosList = videoIds.map { it.asVideoItem() }
        return videosList
    }

    override fun getFavoriteVideos(): List<VideoItem> {

        if (favoriteVideosList.isNotEmpty())
            return favoriteVideosList

        this.favoriteVideosList = videoIds.slice(0..4).map { it.asVideoItem() }
        return this.favoriteVideosList
    }

    private val videoIds = listOf(
        "-GikklXjkgM",
        "opLYavQHBB8",
        "89UusPuz8q4",
        "7Tnq4y7T4xs",
        "KiYHuY3hiZc",
        "KXKP2tDPW4Y",
        "2IAoYnzCTvw",
        "fKY6qYxytLI",
        "9SlKPtUtv6o",
        "AkKjMtBYwDA",
        "rdhPfrj9vgU",
        "xMylnxek5a4",
        "A0I6pNSM14o",
        "LlQW5j0JcCM",
        "Yo_vSI0HgOs",
        "iohKX5gI5Kw",
        "hSsBAT0PbNs",
        "QuHQ5khEn9U",
        "-9zVrVmnbO4",
        "0moEXBqNDZI",
        "PdonCrUxVdQ",
    )
}