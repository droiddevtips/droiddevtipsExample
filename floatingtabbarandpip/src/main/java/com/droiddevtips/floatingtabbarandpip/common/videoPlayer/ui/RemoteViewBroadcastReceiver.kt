package com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class RemoteViewBroadcastReceiver: BroadcastReceiver() {

    companion object {
        const val customActionButton = "customAction"
        const val closeActionButton = "closeAction"
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.i("TAG23","On receive action: ${p1?.action}")
    }
}