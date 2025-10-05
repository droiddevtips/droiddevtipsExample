package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.article.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
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
fun ArticleListItem(
    article: Article,
    modifier: Modifier = Modifier,
    onItemClicked: (Article) -> Unit
) {

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
                onItemClicked(article)
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

            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(actualBoxSize.intValue.dp)
                    .clip(RoundedCornerShape(12.dp)),
                painter = painterResource(id = article.image),
                contentDescription = "Test"
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .weight(1f)
            ) {
                Text(
                    article.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    article.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}