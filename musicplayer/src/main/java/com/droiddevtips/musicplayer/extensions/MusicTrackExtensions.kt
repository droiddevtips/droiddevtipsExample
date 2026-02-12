package com.droiddevtips.musicplayer.extensions

import android.content.ContentResolver
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MediaMetadata.MEDIA_TYPE_MUSIC
import com.droiddevtips.musicplayer.mainView.MusicTrack

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
fun List<MusicTrack>.asMediaItemList(): List<MediaItem> {
    return this.asSequence().map { musicTrack ->
        musicTrack.toMediaItem()
    }.toList()
}

fun MusicTrack.toMediaItem(): MediaItem {

    val uri =
        Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .path(songFile.toString())
            .build()

    val mediaMetaData = toMediaMetaData()

    return MediaItem.Builder()
        .setMediaId(id)
        .setUri(uri)
        .setMediaMetadata(mediaMetaData)
        .build()
}

fun MusicTrack.toMediaMetaData(): MediaMetadata {

    val artworkUri =
        Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .path(thumbnailLarge.toString())
            .build()

    return MediaMetadata.Builder()
        .setTitle(song)
        .setArtist(composer)
        .setSubtitle(credit)
        .setMediaType(MEDIA_TYPE_MUSIC)
        .setArtworkUri(artworkUri)
        .build()
}