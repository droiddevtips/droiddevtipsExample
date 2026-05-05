package com.droiddevtips.spotlight.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class SpotlightViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(SpotlightExampleViewModel::class.java)) {
            return SpotlightExampleViewModel() as T
        }

        throw IllegalArgumentException("Not ${SpotlightExampleViewModel::class.simpleName} class")
    }
}