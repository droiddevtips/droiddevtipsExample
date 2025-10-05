package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.article.ui.grid

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.multiscreensupport.domain.clickableWithPrimaryColorRipple
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.article.data.Article

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun ArticleGridItem(
    article: Article,
    modifier: Modifier = Modifier,
    onItemClicked: (Article) -> Unit
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
                    onItemClicked(article)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp)),
                painter = painterResource(id = article.image),
                contentDescription = "Test"
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    article.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    article.description,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}