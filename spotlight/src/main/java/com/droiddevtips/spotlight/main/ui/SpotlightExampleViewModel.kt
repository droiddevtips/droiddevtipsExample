package com.droiddevtips.spotlight.main.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.spotlight.R
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
                    icon = R.drawable.news_icon,
                    title = "News list item ${index + 1}",
                    subtitle = "News item ${index + 1} content"
                )
            }
            _viewState.update { it.copy(newsItems = dummyNewsItems) }
        }.stateIn(
            viewModelScope, SharingStarted.Companion.WhileSubscribed(5000),
            SpotlightExampleViewState()
        )

//    private val spotlightsPositions = MutableStateFlow<Map<String, SpotlightInfo>>(emptyMap())
//    private val idsShown = MutableStateFlow<Set<String>>(emptySet())
//    private val _spotlightQueue = MutableStateFlow<ArrayDeque<String>>(ArrayDeque())
//    private val _activeSpotlight = MutableStateFlow<SpotlightInfo?>(null)
//    val activeSpotlight: StateFlow<SpotlightInfo?> = _activeSpotlight.asStateFlow()

//    init {
//        Log.i("TAG90", "|----------------------------------|")
//        Log.i("TAG90", "${this::class.java.simpleName} init!")
//        Log.i("TAG90", "|----------------------------------|")
//        observeSpotlightQueue()
//    }

//    private fun observeSpotlightQueue() {
//        viewModelScope.launch {
//            combine(
//                _spotlightQueue,
//                spotlightsPositions,
//                _activeSpotlight
//            ) { queue, positions, activeSpotlight ->
//                Triple(queue, positions, activeSpotlight)
//            }.collect { (queue, positions, activeSpotlight) ->
//
//                if (activeSpotlight != null) return@collect
//
//                val nextSpotlightID = queue.firstOrNull() //?: return@collect
//                Log.i("TAG92", "Next spotlight ID: $nextSpotlightID")
//                if (nextSpotlightID == null)
//                    return@collect
//
//                val nextSpotLightInfo = positions[nextSpotlightID] //?: return@collect
//                Log.i("TAG92", "Next spotlight Info: $nextSpotLightInfo")
//                if (nextSpotLightInfo == null)
//                    return@collect
//
//                Log.i("TAG92", "Continue with queue....... ${queue.joinToString(separator = ",")}")
//
//                _spotlightQueue.update {
//                    ArrayDeque(it).also { queue ->
//                        queue.removeFirst()
//                    }
//                }
//                Log.i("TAG92", "Spotlight queue -> ${_spotlightQueue.value.joinToString(separator = ",")}")
//                Log.i("TAG92", "Active spotlight -> ${_activeSpotlight.value}")
//
//                idsShown.update { it + nextSpotlightID }
//                _activeSpotlight.update {
//                    nextSpotLightInfo
//                }
//            }
//        }
//    }
//
//    fun performAction(action: SpotlightManagerAction) {
//
//        when (action) {
//
//            is SpotlightManagerAction.AddSpotlightInfo -> {
//                Log.i(
//                    "TAG92",
//                    "\n\nAction add -> ${action.spotlightInfo.id} -> spotlights: ${
//                        _spotlightQueue.value.joinToString(separator = ",")
//                    }"
//                )
//
//                // Guard synchronously — before any coroutine or delay — so rapid
//                // duplicate calls (e.g. multiple onGloballyPositioned firings) are
//                // rejected immediately, not after a 1-second race window.
////                if (spotlightsAlreadyShown.contains(action.spotlightInfo.id)) return
////
////                spotlightsAlreadyShown.add(action.spotlightInfo.id)
//
//                spotlightsPositions.update { currentSpotlightMap ->
//                    if (currentSpotlightMap[action.spotlightInfo.id] == action.spotlightInfo)
//                        currentSpotlightMap
//                    else {
//                        _spotlightQueue.update { queue ->
//                            queue.also {
//                                it.add(action.spotlightInfo.id)
//                            }
//                        }
//                        currentSpotlightMap + (action.spotlightInfo.id to action.spotlightInfo)
//                    }
//                }
////                tryContinueWithQueue()
//
//                /*
//                viewModelScope.launch {
//                    delay(1.seconds)
//
//                    if (!spotlightsPositions.value.keys.contains(action.spotlightInfo.id)) {
//                        Log.i("TAG92", "Add spotlight item ${action.spotlightInfo.id}")
////                        spotlights.add(action.spotlightInfo)
//                        Log.i(
//                            "TAG92",
//                            "spotlight items: ${spotlightsPositions.joinToString(separator = ",")} -> current ${_spotlightExampleViewState.value.spotlightInfo}\n\n"
//                        )
//                        showSpotlight()
//                    }
//                }
//                */
//
//            }
//
//            is SpotlightManagerAction.OnDismissSpotlight -> {
//                Log.i("TAG90", "On Dismiss Spotlight!!")
////                _spotlightExampleViewState.update {
////                    SpotlightExampleViewState()
////                }
////                showSpotlight()
//
//                _activeSpotlight.update { null }
//                tryContinueWithQueue()
//            }
//        }
//    }
//
//    private fun tryContinueWithQueue() {
//        _spotlightQueue.update { ArrayDeque(it) }
//    }
}