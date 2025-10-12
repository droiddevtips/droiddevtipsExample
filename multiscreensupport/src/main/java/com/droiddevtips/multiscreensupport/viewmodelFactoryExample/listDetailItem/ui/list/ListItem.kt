package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.droiddevtips.multiscreensupport.common.data.AppString
import com.droiddevtips.multiscreensupport.common.data.Drawable
import com.droiddevtips.multiscreensupport.common.extensions.clickableWithPrimaryColorRipple
import com.droiddevtips.multiscreensupport.util.Device
import com.droiddevtips.multiscreensupport.util.DeviceOrientation
import com.droiddevtips.multiscreensupport.util.currentWindowSize
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ViewType

/**
 * The list item composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun ListItem(
    item: ListDetailItem,
    modifier: Modifier = Modifier,
    onItemClicked: (ListDetailItem) -> Unit
) {

    val currentWindowSize = currentWindowSize()
    val actualBoxSize = rememberSaveable { mutableIntStateOf(150) }
    val density = LocalDensity.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .onSizeChanged({ size ->

                val widthInDp = with(density) {
                    size.width.toDp()
                }.value.toInt()

                val boxSize = widthInDp / 4

                if (actualBoxSize.intValue != boxSize) {
                    val newBoxSize = boxSize
                    actualBoxSize.intValue = if (newBoxSize <= 100) newBoxSize else 100
                }
            })
            .clickableWithPrimaryColorRipple {
                onItemClicked(item)
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 250.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            AsyncImage(
                model = item.image,
                modifier = Modifier
                    .size(actualBoxSize.intValue.dp)
                    .then(
                        if (item.viewType == ViewType.Follower) {
                            Modifier.clip(CircleShape)
                        } else {
                            Modifier.clip(RoundedCornerShape(12.dp))
                        }
                    ),
                contentDescription = when (item.viewType) {
                    ViewType.Home -> stringResource(id = AppString.home_content_description)
                    ViewType.News -> stringResource(id = AppString.news_content_description)
                    ViewType.Follower -> stringResource(id = AppString.follower_content_description)
                },
                placeholder = painterResource(id = when (item.viewType) {
                    ViewType.Home, ViewType.News  -> Drawable.article_image
                    ViewType.Follower -> Drawable.follower
                })
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .weight(1f)
            ) {
                Text(
                    item.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    item.description,
                    maxLines = if (currentWindowSize.device == Device.Tablet && currentWindowSize.orientation == DeviceOrientation.Landscape) 3 else 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}