@file:Suppress("UNCHECKED_CAST")

package com.droiddevtips.musicplayer.mainView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class MusicPlayerViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(MusicPlayerViewModel::class.java)) {
            return MusicPlayerViewModel() as T
        }
        throw IllegalArgumentException("Not ${MusicPlayerViewModel::class.java.simpleName}")
    }
}