package com.droiddevtips.visibilitychanged.ui.listExample.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutBoundsHolder
import androidx.compose.ui.layout.onVisibilityChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.droiddevtips.visibilitychanged.data.AppString

/**
 * A dummy list item displayed in a sample [LazyColumn] to demonstrate the
 * `onVisibilityChanged` API. The item emits visibility change callbacks as it
 * scrolls into and out of the viewport. When it is visible the background color changes to [Color.Blue] and [Color.Red] when invisible.
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun DummyListItem(
    index: Int,
    isVisible: Boolean,
    viewport: LayoutBoundsHolder,
    modifier: Modifier = Modifier,
    onVisibilityChanged: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .onVisibilityChanged(
                minFractionVisible = 1f,
                minDurationMs = 200,
                viewportBounds = viewport,
                callback = onVisibilityChanged
            )
            .then(
                if (isVisible) {
                    Modifier.background(color = Color.Blue)
                } else {
                    Modifier.background(color = Color.Red)
                }
            )
    ) {
        Text(
            text = stringResource(
                AppString.list_item_title,
                index,
                stringResource( if (isVisible) AppString.item_visible else AppString.item_not_visible)
            ),
            modifier = Modifier.padding(all = 16.dp)
        )
    }
}