package com.droiddevtips.multiscreensupport.rememberSaveableExample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.droiddevtips.multiscreensupport.common.data.AppString
import com.droiddevtips.multiscreensupport.util.AppWindowSize
import com.droiddevtips.multiscreensupport.util.currentWindowSize

/**
 * The remember saveable composable example
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun RememberSaveableExample(modifier: Modifier = Modifier) {

    val currentWindowSize = currentWindowSize()
    val number = rememberSaveable { mutableIntStateOf(1) }

    val width = when (currentWindowSize.windowWidthSize) {
        AppWindowSize.Compact -> stringResource(id = AppString.compact)
        AppWindowSize.Medium -> stringResource(id = AppString.medium)
        AppWindowSize.Expanded -> stringResource(id = AppString.expanded)
    }

    val height = when (currentWindowSize.windowHeightSize) {
        AppWindowSize.Compact -> stringResource(id = AppString.compact)
        AppWindowSize.Medium -> stringResource(id = AppString.medium)
        AppWindowSize.Expanded -> stringResource(id = AppString.expanded)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp, alignment = Alignment.CenterVertically)
    ) {

        Text(
            text = "${String.format(stringResource(id = AppString.width), width)}\n${
                String.format(
                    stringResource(id = AppString.height),
                    height
                )
            }",
            modifier = modifier
        )

        Text(
            text = "${number.intValue}"
        )

        Button(onClick = {
            number.intValue = ++number.intValue
        }) {
            Text(text = stringResource(AppString.increase_number))
        }
    }
}