package com.droiddevtips.spotlight

import com.droiddevtips.spotlight.spotlightFeature.SpotlightInfo

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
sealed interface SpotlightViewModelAction {
    data class AddSpotlightInfo(val spotlightInfo: SpotlightInfo): SpotlightViewModelAction
    data object OnDismissSpotlight: SpotlightViewModelAction
}