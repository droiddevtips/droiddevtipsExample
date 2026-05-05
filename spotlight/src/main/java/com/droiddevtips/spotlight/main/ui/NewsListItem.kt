package com.droiddevtips.spotlight.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.droiddevtips.spotlight.main.data.NewsItem

/**
 * The news list item composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun NewsListItem(
    newsItem: NewsItem,
    modifier: Modifier = Modifier,
    onClicked: (NewsItem) -> Unit
) {

    Card(onClick = {
        onClicked(newsItem)
    }) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {

            Image(
                painter = painterResource(newsItem.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
            )

            Column(
                modifier = Modifier.height(50.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = newsItem.title, style = MaterialTheme.typography.titleMedium)
                Text(text = newsItem.subtitle, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}