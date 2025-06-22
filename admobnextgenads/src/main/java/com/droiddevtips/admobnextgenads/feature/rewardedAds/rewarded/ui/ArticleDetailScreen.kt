@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.droiddevtips.admobnextgenads.R
import com.droiddevtips.admobnextgenads.core.data.AppString
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdListDisplayItem

/**
 * This is the article detail composable screen
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun ArticleDetailScreen(
    data: RewardedAdListDisplayItem?
) {

    if (data == null)
        return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Image(
            painter = painterResource(id = data.image),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            contentDescription = null
        )

        Text(
            text = data.title,
            style = MaterialTheme.typography.titleLarge.copy(
                color = if (data.premium) colorResource(
                    R.color.droid_dev_tips_green
                ) else {
                    Color.Unspecified
                }
            )
        )

        Text(
            text = stringResource(id = if (data.premium) AppString.article_content_premium else AppString.article_content)
        )
    }
}
