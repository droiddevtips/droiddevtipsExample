package com.droiddevtips.musicplayer.ui.mainView.data

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.droiddevtips.musicplayer.R

/**
 * The music list item preview provider
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class MusicListItemPreviewProvider : PreviewParameterProvider<MusicTrack> {

    override val values: Sequence<MusicTrack>
        get() = sequenceOf(
            MusicTrack(
                id = "1",
                song = "Alone",
                composer = "Alex Productions",
                songFile = R.raw.alone,
                thumbnail = R.mipmap.dawn_thumb,
                thumbnailLarge = R.drawable.dragon_castle_thumb_large,
                credit = "Music powered by BreakingCopyright"
            )
        )
}