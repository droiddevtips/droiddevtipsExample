package com.droiddevtips.musicplayer

import android.app.PendingIntent
import android.content.Intent
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class MusicPlayerService : MediaSessionService() {

   private var mediaSession: MediaSession? = null
   private var mediaPlayer: ExoPlayer? = null

    @UnstableApi
    override fun onCreate() {
        super.onCreate()
        ExoPlayer.Builder(this).build().also { mediaPlayer ->
            val openAppIntent = Intent(this, MusicPlayerActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }

            val openAppPendingIntent = PendingIntent.getActivity(this,0,openAppIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
            mediaSession = MediaSession.Builder(this, mediaPlayer).setSessionActivity(openAppPendingIntent).build()
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? = mediaSession

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
            mediaPlayer = null
        }
        super.onDestroy()
    }
}