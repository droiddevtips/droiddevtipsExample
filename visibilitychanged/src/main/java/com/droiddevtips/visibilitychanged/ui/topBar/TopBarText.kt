package com.droiddevtips.visibilitychanged.ui.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.droiddevtips.visibilitychanged.R
/**
 * Displays the title text within the top app bar of a [Scaffold].
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun TopBarText(visibleTabText: String, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = stringResource(R.string.tab_view_visible, visibleTabText),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        )
    }
}