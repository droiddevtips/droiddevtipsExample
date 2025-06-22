package com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.fetcher.AdFetcher
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdViewAction
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdViewState
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.repository.RewardedAdNewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    private val adFetcher: AdFetcher,
    private val repository: RewardedAdNewsRepository
) : ViewModel() {

    companion object {
        val savedStateHandleKey = "${this::class.java.simpleName}"
    }

    private var _rewardedAdViewState = MutableStateFlow(
        savedStateHandle.get<RewardedAdViewState>(savedStateHandleKey) ?: RewardedAdViewState()
    )

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

        delay(3000)
        _rewardedAdViewState.update { it.copy(showRewardedAdButton = true) }
        saveState()

    }

    fun performAction(action: RewardedAdViewAction) {

        when(action) {
            is RewardedAdViewAction.DismissRewardedAd -> {
                _rewardedAdViewState.update { it.copy(showRewardedAdButton = false, showRewardedAd = false, rewardedAdView = null) }
            }

            is RewardedAdViewAction.LoadAndShowRewardedAd -> {
                _rewardedAdViewState.update { it.copy(isLoadingAd = true) }
                adFetcher.fetchRewardedAd(NextGenAdUnit.RewardedAd) { rewardedAd ->
                    Log.i("TAG23","Rewarded ad: $rewardedAd")
                    rewardedAd?.let {
                        _rewardedAdViewState.update { it.copy(showRewardedAd = true, rewardedAdView = rewardedAd) }
                    }?:run {
                        _rewardedAdViewState.update { it.copy(isLoadingAd = false, showRewardedAdButton = false, showRewardedAd = false, rewardedAdView = null) }
                    }
                }
            }

            is RewardedAdViewAction.OnRewardedAdCompleted -> {
                _rewardedAdViewState.update { it.copy(isLoadingAd = false, showRewardedAdButton = false, showRewardedAd = false, rewardedAdView = null, credit = rewardedAdViewState.value.credit + 1) }
            }

            is RewardedAdViewAction.SubtractCredit -> {
                _rewardedAdViewState.update { it.copy(credit = rewardedAdViewState.value.credit - 1) }
            }
        }


    }

    fun loadAndShowRewardedAd() {

    }

    fun dismissRewardedAd() {

    }

    private fun saveState() {
        savedStateHandle[savedStateHandleKey] = _rewardedAdViewState.value
    }
}