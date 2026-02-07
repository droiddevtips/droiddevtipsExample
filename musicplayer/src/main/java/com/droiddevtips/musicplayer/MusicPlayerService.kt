package com.droiddevtips.musicplayer

import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

const val NEXT = "next"
const val PREV = "prev"
const val PLAY_PAUSE = "play_pause"

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
            mediaSession = MediaSession.Builder(this, mediaPlayer).build()
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


//    @UnstableApi
//    private fun createNotificationProvider(): MediaNotification.Provider {
//
//        return DefaultMediaNotificationProvider.Builder(this)
//            .setChannelId(MusicPlayerApp.CHANEL_ID)
//            .setChannelName(DEFAULT_CHANNEL_NAME_RESOURCE_ID)
//            .build()
//
//    }

    /*
    private fun sendNotification(musicTrack: MusicTrack) {



//        val style = NotificationCompat

        mediaPlayer?.let { player ->
            val notification =
                NotificationCompat.Builder(this, MusicPlayerApp.CHANEL_ID)
                    .setContentTitle("Title")
                    .setContentText("Content text")
                    .addAction(R.drawable.prev_button,"prev", createPreviousPendingIntent())
                    .addAction(if (player.isPlaying) R.drawable.pause_button else R.drawable.play_button,"play_pause", createPlayPausePendingIntent())
                    .addAction(R.drawable.next_button,"next", createNextPendingIntent())
                    .setSmallIcon(R.drawable.app_icon_background_foreground)
                    .setLargeIcon(BitmapFactory.decodeResource(resources,R.mipmap.alone))
                    .build()
        }




    }
    */

    /*
    fun createPreviousPendingIntent(): PendingIntent {

        val intent = Intent(this, MusicPlayerService::class.java).apply {
            action = PREV
        }
        return PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

    }

    fun createNextPendingIntent(): PendingIntent {

        val intent = Intent(this, MusicPlayerService::class.java).apply {
            action = NEXT
        }
        return PendingIntent.getService(this, 2, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

    }

    fun createPlayPausePendingIntent(): PendingIntent {

        val intent = Intent(this, MusicPlayerService::class.java).apply {
            action = PLAY_PAUSE
        }
        return PendingIntent.getService(this, 3, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

    }
    */


}