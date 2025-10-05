package com.droiddevtips.multiscreensupport.domain

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

fun Modifier.clickableWithPrimaryColorRipple(radius: Dp = Dp.Unspecified, onClick: () -> Unit): Modifier = this.composed {

    val interactionSource = remember { MutableInteractionSource() }

    clickable(
        interactionSource = interactionSource,
        indication = ripple(
            radius = radius,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
        ),
        onClick = onClick
    )
}