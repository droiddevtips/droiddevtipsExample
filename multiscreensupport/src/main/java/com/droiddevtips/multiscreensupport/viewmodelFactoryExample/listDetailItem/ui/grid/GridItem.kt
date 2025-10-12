package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.droiddevtips.multiscreensupport.common.data.AppString
import com.droiddevtips.multiscreensupport.common.data.Drawable
import com.droiddevtips.multiscreensupport.common.extensions.clickableWithPrimaryColorRipple
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ViewType

/**
 * The grid item composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun GridItem(
    item: ListDetailItem,
    modifier: Modifier = Modifier,
    onItemClicked: (ListDetailItem) -> Unit
) {
    Box(modifier = modifier.padding(all = 16.dp)) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(all = 16.dp)
                .clickableWithPrimaryColorRipple {
                    onItemClicked(item)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            AsyncImage(
                model = item.image,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentDescription = when (item.viewType) {
                    ViewType.Home -> stringResource(id = AppString.home_content_description)
                    ViewType.News -> stringResource(id = AppString.news_content_description)
                    ViewType.Follower -> stringResource(id = AppString.follower_content_description)
                },
                placeholder = painterResource(
                    id = when (item.viewType) {
                        ViewType.Home, ViewType.News -> Drawable.article_image
                        ViewType.Follower -> Drawable.follower
                    }
                )
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = if (item.viewType == ViewType.Follower) Alignment.CenterHorizontally else Alignment.Start
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = item.description,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}