package com.droiddevtips.admobnextgenads.feature.rewardedAds.interstitial

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.fetcher.AdFetcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class RewardedInterstitialViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val adFetcher: AdFetcher,
    private val repository: RewardedInterstitialAdNewsRepository
) : ViewModel() {

    companion object {
        val savedStateHandleKey = "${this::class.java.simpleName}"
    }

    private var _rewardedInterstitialAdViewState = MutableStateFlow(
        savedStateHandle.get<RewardedInterstitialAdViewState>(savedStateHandleKey)
            ?: RewardedInterstitialAdViewState()
    )

    val rewardedInterstitialAdViewState: StateFlow<RewardedInterstitialAdViewState>
        get() = _rewardedInterstitialAdViewState.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Main).launch {
            preFetchRewardedAd()
            loadNews()
        }
    }

    fun performAction(action: RewardedInterstitialAdViewAction) {

        when (action) {
            is RewardedInterstitialAdViewAction.DismissRewardedInterstitialAd -> {

                if (action.rewardClaimed) {
                    _rewardedInterstitialAdViewState.update {
                        it.copy(
                            navigateToDetail = true
                        )
                    }
                } else {
                    _rewardedInterstitialAdViewState.update {
                        it.copy(
                            isLoadingAd = false,
                            premiumArticleData = null,
                            showRewardedInterstitial = false,
                            rewardedInterstitialAdView = null
                        )
                    }
                }
            }

            is RewardedInterstitialAdViewAction.ShowRewardedInterstitialAdDialog -> {
                _rewardedInterstitialAdViewState.update {
                    it.copy(
                        premiumArticleData = action.premiumArticleData,
                        showRewardedInterstitialDialog = true
                    )
                }
            }

            is RewardedInterstitialAdViewAction.DismissRewardedInterstitialAdDialog -> {
                _rewardedInterstitialAdViewState.update {
                    it.copy(
                        showRewardedInterstitialDialog = false
                    )
                }
            }

            is RewardedInterstitialAdViewAction.ShowRewardedInterstitialAd -> {

                if (rewardedInterstitialAdViewState.value.isLoadingAd || rewardedInterstitialAdViewState.value.rewardedInterstitialAdView == null)
                    return

                viewModelScope.launch {
                    _rewardedInterstitialAdViewState.update {
                        it.copy(
                            showRewardedInterstitialDialog = false
                        )
                    }
                    delay(500.milliseconds)
                    _rewardedInterstitialAdViewState.update {
                        it.copy(
                            showRewardedInterstitial = true
                        )
                    }
                }
            }

            is RewardedInterstitialAdViewAction.ResetViewState -> {
                _rewardedInterstitialAdViewState.update {
                    it.copy(
                        isLoadingAd = false,
                        premiumArticleData = null,
                        showRewardedInterstitial = false,
                        rewardedInterstitialAdView = null
                    )
                }
            }
        }
    }

    private fun preFetchRewardedAd() {
        adFetcher.fetchRewardedInterstitialAd(adUnit = NextGenAdUnit.RewardedInterstitialAd) { rewardedInterstitialAd ->
            Log.i("TAG12", "[Rewarded interstitial ads] - $rewardedInterstitialAd")
            println("[Rewarded interstitial ads] - $rewardedInterstitialAd")
            viewModelScope.launch {
                rewardedInterstitialAd?.let { _rewardedInterstitialAd ->
                    _rewardedInterstitialAdViewState.update {
                        it.copy(
                            isLoadingAd = false,
                            rewardedInterstitialAdView = _rewardedInterstitialAd
                        )
                    }
                } ?: run {
                    _rewardedInterstitialAdViewState.update {
                        it.copy(
                            isLoadingAd = false,
                            premiumArticleData = null,
                            rewardedInterstitialAdView = null
                        )
                    }
                }
            }
        }
    }

    private suspend fun loadNews() {
        _rewardedInterstitialAdViewState.update { RewardedInterstitialAdViewState() }
        val newsList = repository.loadRewardedInterstitialDummyNews()
        _rewardedInterstitialAdViewState.update { it.copy(isLoading = false, newsList = newsList) }
        saveState()
    }

    private fun saveState() {
        savedStateHandle[savedStateHandleKey] = _rewardedInterstitialAdViewState.value
    }

}