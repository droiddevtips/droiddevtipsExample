package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample

import androidx.lifecycle.ViewModel
import com.droiddevtips.multiscreensupport.data.MultiScreenSupportAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class MultiScreenViewModel: ViewModel() {

    private var _counter = MutableStateFlow(0)

    val counter: StateFlow<Int>
        get() = _counter.asStateFlow()

    fun performAction(action: MultiScreenSupportAction) {

        when(action) {
            MultiScreenSupportAction.Increase -> {
                _counter.value = ++_counter.value
            }
        }
    }

}