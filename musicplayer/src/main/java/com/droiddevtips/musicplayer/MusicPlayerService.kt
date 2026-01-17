package com.droiddevtips.musicplayer

import android.app.Service
import android.content.Intent
import android.media.browse.MediaBrowser
import android.os.IBinder
import androidx.media3.common.MediaItem

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class MusicPlayerService: Service() {

    val musicServiceBinder: MusicServiceBinder = MusicServiceBinder()

    override fun onBind(p0: Intent?): IBinder = musicServiceBinder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun sendNotification(mediaItem: MediaItem) {


    }


}