package com.droiddevtips.musicplayer.mainView

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.droiddevtips.musicplayer.R

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class MusicListItemPreviewProvider : PreviewParameterProvider<MusicTrack> {

    override val values: Sequence<MusicTrack>
        get() = sequenceOf(
            MusicTrack(
                song = "Alone",
                composer = "Alex Productions",
                songFile = R.raw.alone,
                thumbnail = R.mipmap.dawn_thumb,
                credit = "Music powered by BreakingCopyright"
            )
        )
}