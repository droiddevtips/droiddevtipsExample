@file:Suppress("UNCHECKED_CAST")

package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class MultiScreenViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(MultiScreenViewModel::class.java)) {

            return MultiScreenViewModel() as T

        }

        throw IllegalArgumentException("Not ${MultiScreenViewModel::class.simpleName} class")
    }

}