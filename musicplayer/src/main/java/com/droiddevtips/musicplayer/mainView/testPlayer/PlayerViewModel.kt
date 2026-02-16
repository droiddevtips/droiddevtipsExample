package com.droiddevtips.musicplayer.mainView.testPlayer

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */

enum class PlayerUiState {
    HIDDEN,
    MINI,
    EXPANDED
}

class PlayerViewModel: ViewModel() {

    var playerState by mutableStateOf(PlayerUiState.HIDDEN)
        private set

    fun showMini() {
        Log.i("TAG23","Show mini player")
        playerState = PlayerUiState.MINI }
    fun expand() {
        Log.i("TAG23","Expand mini player")
        playerState = PlayerUiState.EXPANDED }
    fun hide() {
        Log.i("TAG23","Hide mini player")
        playerState = PlayerUiState.HIDDEN }

}