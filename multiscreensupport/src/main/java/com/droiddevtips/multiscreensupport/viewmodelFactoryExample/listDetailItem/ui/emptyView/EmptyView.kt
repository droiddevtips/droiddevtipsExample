package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.emptyView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.droiddevtips.multiscreensupport.common.data.AppString
import com.droiddevtips.multiscreensupport.common.data.Drawable
import com.droiddevtips.multiscreensupport.util.Device
import com.droiddevtips.multiscreensupport.util.DeviceOrientation
import com.droiddevtips.multiscreensupport.util.currentWindowSize

/**
 * The empty composable view placeholder
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun EmptyView(modifier: Modifier = Modifier) {

    val currentWindowSize = currentWindowSize()

    Box(
        modifier = modifier.then(
            if (currentWindowSize.device == Device.Tablet && currentWindowSize.orientation == DeviceOrientation.Landscape) {
                Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            } else {
                Modifier
            }
        )
    ) {

        Column(
            modifier = modifier
                .then(
                    if (currentWindowSize.device == Device.Tablet && currentWindowSize.orientation == DeviceOrientation.Landscape) {
                        Modifier
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(12.dp)
                            )
                    } else {
                        Modifier
                    }
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterVertically
            )
        ) {

            Image(
                painter = painterResource(Drawable.empty_icon),
                modifier = Modifier.size(60.dp),
                contentDescription = null
            )

            Text(text = stringResource(id = AppString.no_article_found))
        }
    }
}