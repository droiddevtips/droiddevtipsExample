package com.droiddevtips.spotlight.spotlight.domain

import android.util.Log
import com.droiddevtips.spotlight.spotlight.data.SpotlightManagerAction
import com.droiddevtips.spotlight.spotlight.data.SpotlightInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The spotlight manager object, a singleton object.
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
object SpotlightManager {

    private val spotlightsPositions = MutableStateFlow<Map<String, SpotlightInfo>>(emptyMap())
    private val idsShown = MutableStateFlow<Set<String>>(emptySet())
    private val _spotlightQueue = MutableStateFlow<ArrayDeque<String>>(ArrayDeque())
    private val _activeSpotlight = MutableStateFlow<SpotlightInfo?>(null)
    val activeSpotlight: StateFlow<SpotlightInfo?> = _activeSpotlight.asStateFlow()

    init {
        observeSpotlightQueue()
    }

    private fun observeSpotlightQueue() {
        CoroutineScope(Dispatchers.Main).launch {
            combine(
                _spotlightQueue,
                spotlightsPositions,
                _activeSpotlight
            ) { queue, positions, activeSpotlight ->
                Triple(queue, positions, activeSpotlight)
            }.collect { (queue, positions, activeSpotlight) ->

                if (activeSpotlight != null) return@collect

                val nextSpotlightID = queue.firstOrNull() //?: return@collect
                Log.i("TAG92", "Next spotlight ID: $nextSpotlightID")
                if (nextSpotlightID == null)
                    return@collect

                val nextSpotLightInfo = positions[nextSpotlightID] //?: return@collect
                Log.i("TAG92", "Next spotlight Info: $nextSpotLightInfo")
                if (nextSpotLightInfo == null)
                    return@collect

                Log.i("TAG92", "Continue with queue....... ${queue.joinToString(separator = ",")}")

                _spotlightQueue.update {
                    ArrayDeque(it).also { queue ->
                        queue.removeFirst()
                    }
                }
                Log.i("TAG92", "Spotlight queue -> ${_spotlightQueue.value.joinToString(separator = ",")}")
                Log.i("TAG92", "Active spotlight -> ${_activeSpotlight.value}")

                idsShown.update { it + nextSpotlightID }
                _activeSpotlight.update {
                    nextSpotLightInfo
                }
            }
        }
    }

    fun performAction(action: SpotlightManagerAction) {

        when (action) {

            is SpotlightManagerAction.AddSpotlightInfo -> {
                Log.i(
                    "TAG92",
                    "\n\nAction add -> ${action.spotlightInfo.id} -> spotlights: ${
                        _spotlightQueue.value.joinToString(separator = ",")
                    }"
                )

                spotlightsPositions.update { currentSpotlightMap ->
                    if (currentSpotlightMap[action.spotlightInfo.id] == action.spotlightInfo)
                        currentSpotlightMap
                    else {
                        _spotlightQueue.update { queue ->
                            queue.also {
                                it.add(action.spotlightInfo.id)
                            }
                        }
                        currentSpotlightMap + (action.spotlightInfo.id to action.spotlightInfo)
                    }
                }
            }

            is SpotlightManagerAction.OnDismissSpotlight -> {
                Log.i("TAG90", "On Dismiss Spotlight!!")
                _activeSpotlight.update { null }
                tryContinueWithQueue()
            }
        }
    }

    private fun tryContinueWithQueue() {
        _spotlightQueue.update { ArrayDeque(it) }
    }
}