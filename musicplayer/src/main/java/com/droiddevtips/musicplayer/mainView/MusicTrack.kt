package com.droiddevtips.musicplayer.mainView

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
    val song: String,
    val composer: String,
    @field:RawRes val songFile: Int,
    @field:RawRes val thumbnail: Int,
    val credit: String
) : Parcelable