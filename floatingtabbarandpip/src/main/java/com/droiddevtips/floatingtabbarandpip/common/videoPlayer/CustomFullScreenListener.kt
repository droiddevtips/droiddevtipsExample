package com.droiddevtips.floatingtabbarandpip.common.videoPlayer

import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class CustomFullScreenListener(private val activity: Activity): FullscreenListener {

    private var fullscreenView: View? = null

    override fun onEnterFullscreen(
        fullscreenView: View,
        exitFullscreen: () -> Unit
    ) {

        this.fullscreenView =
            fullscreenView
        val window = activity.window
        (window.decorView as FrameLayout).addView(
            fullscreenView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        activity.requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )
        val controller = WindowInsetsControllerCompat(
            window,
            window.decorView
        )
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        Log.i("TAG23", "enter full screen")

    }

    override fun onExitFullscreen() {
        Log.i("TAG23", "exit full screen")
        val window = activity.window
        (window.decorView as FrameLayout).removeView(
            fullscreenView
        )

        fullscreenView = null
        activity.requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        WindowCompat.setDecorFitsSystemWindows(window, true)
        val controller = WindowInsetsControllerCompat(
            window,
            window.decorView
        )
        controller.show(WindowInsetsCompat.Type.systemBars())
    }
}