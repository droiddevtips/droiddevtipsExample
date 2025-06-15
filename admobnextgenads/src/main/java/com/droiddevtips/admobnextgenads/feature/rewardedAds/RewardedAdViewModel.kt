package com.droiddevtips.admobnextgenads.feature.rewardedAds

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class RewardedAdViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: RewardedAdNewsRepository
) : ViewModel() {

    companion object {
        val savedStateHandleKey = "${this::class.java.simpleName}"
    }

    private var _rewardedAdViewState = MutableStateFlow(savedStateHandle.get<RewardedAdViewState>(savedStateHandleKey) ?: RewardedAdViewState())

    val rewardedAdViewState: StateFlow<RewardedAdViewState>
        get() = _rewardedAdViewState.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Main).launch {
            loadNews()
        }
    }

    private suspend fun loadNews() {
        _rewardedAdViewState.update { RewardedAdViewState() }
        val newsList = repository.loadNews()
        _rewardedAdViewState.update { it.copy(isLoading = false, newsList = newsList) }
        saveState()
    }

    private fun saveState() {
        savedStateHandle[savedStateHandleKey] = _rewardedAdViewState.value
    }
}