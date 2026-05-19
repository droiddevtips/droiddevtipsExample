package com.droiddevtips.spotlight.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.spotlight.common.SpotlightResDrawable
import com.droiddevtips.spotlight.main.data.NewsItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * The spotlight example view model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
class SpotlightExampleViewModel : ViewModel() {

    private val _viewState =
        MutableStateFlow(SpotlightExampleViewState())
    val viewState: StateFlow<SpotlightExampleViewState>
        get() = _viewState.asStateFlow().onStart {

            val dummyNewsItems = List(60) { index ->
                NewsItem(
                    icon = SpotlightResDrawable.news_icon,
                    title = "News list item ${index + 1}",
                    subtitle = "News item ${index + 1} content"
                )
            }
            _viewState.update { it.copy(newsItems = dummyNewsItems) }
        }.stateIn(
            viewModelScope, SharingStarted.Companion.WhileSubscribed(5000),
            SpotlightExampleViewState()
        )
}