package com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

/**
 * The Remote [BroadcastReceiver]
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class RemoteViewBroadcastReceiver: BroadcastReceiver() {

    companion object {
        const val ACTION_CLOSE_BUTTON = "closeAction"
        const val ACTION_PLAY_BUTTON = "playAction"
        const val ACTION_PAUSE_BUTTON = "pauseAction"
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p0 == null || p1 == null)
            return

        Toast.makeText(p0,"${p1.action} received!", Toast.LENGTH_SHORT).show()
    }
}