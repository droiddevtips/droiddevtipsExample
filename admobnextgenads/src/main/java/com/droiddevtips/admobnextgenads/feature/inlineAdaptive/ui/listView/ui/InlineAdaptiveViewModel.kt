package com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui.listView.ui

import androidx.lifecycle.ViewModel
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui.listView.data.ListItem
import com.droiddevtips.admobnextgenads.feature.inlineAdaptive.ui.listView.data.ListViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class InlineAdaptiveViewModel: ViewModel() {

    private var _inlineAdaptiveViewState = MutableStateFlow(ListViewState())

    val inlineAdaptiveViewState: StateFlow<ListViewState>
        get() = _inlineAdaptiveViewState.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Main).launch {
            loadData()
        }
    }

    private suspend fun loadData() {

        delay(2.seconds) // Simulate remote server api call
        _inlineAdaptiveViewState.update { it.copy(isLoading = false, items = getDummyItemList()) }

    }

    private fun getDummyItemList(): List<ListItem> {

        val items = ArrayList<ListItem>()

        for (item in 1..50) {
            items.add(ListItem(title = "List item $item"))
            if (item % 10 == 0) {
                items.add(ListItem(title = "", adUnit = NextGenAdUnit.BannerAd(_key = "item_$item")))
            }
        }

        return items
    }
}
