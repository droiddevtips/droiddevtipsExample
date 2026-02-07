package com.droiddevtips.typography.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * The modifier extensions
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
fun Modifier.clickableWithPrimaryColorRipple(radius: Dp = Dp.Unspecified, onClick: () -> Unit): Modifier = this.composed {

    val interactionSource = remember { MutableInteractionSource() }

    clickable(
        interactionSource = interactionSource,
        indication = ripple(
            radius = radius,
            color = Color.DarkGray
        ),
        onClick = onClick
    )
}

// MaterialTheme.colorScheme.primary
// .copy(alpha = if (isSystemInDarkTheme()) 0.15f else 0.58f)