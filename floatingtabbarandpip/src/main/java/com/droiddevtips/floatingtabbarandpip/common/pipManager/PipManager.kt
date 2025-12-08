package com.droiddevtips.floatingtabbarandpip.common.pipManager

/**
 * The Picture-in-picture manager
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
object PipManager {

    private var _isInPipMode: Boolean = false
    val isInPipMode: Boolean
        get() = _isInPipMode

    fun updatePipModeState(pipMode: Boolean) {
        this._isInPipMode = pipMode
    }
}