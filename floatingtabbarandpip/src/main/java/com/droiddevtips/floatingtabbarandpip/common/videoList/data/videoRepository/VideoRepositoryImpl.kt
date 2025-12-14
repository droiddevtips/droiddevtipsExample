package com.droiddevtips.floatingtabbarandpip.common.videoList.data.videoRepository

import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoDetails
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoItem
import com.droiddevtips.floatingtabbarandpip.extensions.asVideoItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

/**
 * The videos repository implementation
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
object VideoRepositoryImpl: VideoRepository {


    val httpClient = HttpClient(Android) {
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
        }
        install(ContentNegotiation) {
            json()
        }
    }

    private var favoriteVideosList: List<VideoItem> = emptyList()
    private var videosList: List<VideoItem> = emptyList()

    override fun getVideos(): List<VideoItem> {

        if (videosList.isNotEmpty())
            return videosList

        this.videosList = videoIds.map { it.asVideoItem(favorite = false) }
        return videosList
    }

    override fun getFavoriteVideos(): List<VideoItem> {

        if (favoriteVideosList.isNotEmpty())
            return favoriteVideosList

        this.favoriteVideosList = videoIds.slice(0..4).map { it.asVideoItem(favorite = true) }
        return this.favoriteVideosList
    }

    override suspend fun loadVideoDetail(videoID: String): VideoDetails? = withContext(Dispatchers.IO) {

        try {
            val requestUrl = "https://www.youtube.com/oembed?url=https://www.youtube.com/watch?v=$videoID&format=json"
            val videoDetailResponse = httpClient.get(requestUrl)

            withContext(Dispatchers.Main) {
                if (videoDetailResponse.status.isSuccess()) {
                    val json = Json { ignoreUnknownKeys = true }
                    val videoDetailResponse = json.decodeFromString<VideoDetails>(videoDetailResponse.body())
                    videoDetailResponse
                } else {
                    null
                }
            }
        }catch (e: Exception) {
            println("Error requesting video detail: ${e.message}")
            withContext(Dispatchers.Main) {
                null
            }
        }
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