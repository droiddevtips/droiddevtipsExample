package com.droiddevtips.spotlight.spotlight.data

/**
 * The spotlight manager action interface
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
sealed interface SpotlightManagerAction {
    data class AddSpotlightInfo(val spotlightInfo: SpotlightInfo): SpotlightManagerAction
    data object OnDismissSpotlight: SpotlightManagerAction
}