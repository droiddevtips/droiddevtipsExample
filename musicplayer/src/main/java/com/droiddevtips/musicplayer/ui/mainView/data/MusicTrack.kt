package com.droiddevtips.musicplayer.ui.mainView.data

import android.os.Parcelable
import androidx.annotation.RawRes
import kotlinx.parcelize.Parcelize

/**
 * The music track data model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Parcelize
data class MusicTrack(
    val id: String,
    val song: String,
    val composer: String,
    @field:RawRes val songFile: Int,
    @field:RawRes val thumbnail: Int,
    @field:RawRes val thumbnailLarge: Int,
    val credit: String
) : Parcelable