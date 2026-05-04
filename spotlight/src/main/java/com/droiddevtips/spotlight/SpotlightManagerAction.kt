package com.droiddevtips.spotlight

import com.droiddevtips.spotlight.spotlightFeature.SpotlightInfo

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
sealed interface SpotlightManagerAction {
    data class AddSpotlightInfo(val spotlightInfo: SpotlightInfo): SpotlightManagerAction
    data object OnDismissSpotlight: SpotlightManagerAction
}