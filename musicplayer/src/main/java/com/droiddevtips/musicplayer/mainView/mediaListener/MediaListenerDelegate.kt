package com.droiddevtips.musicplayer.mainView.mediaListener

import android.util.Log
import androidx.media3.common.Player

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class MediaListenerDelegate(private val mediaListener: MediaListener): Player.Listener {

    init {
        mediaListener.test("Test5674")
    }



}