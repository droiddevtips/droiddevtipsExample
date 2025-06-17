package com.droiddevtips.admobnextgenads.feature.rewardedAds

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.droiddevtips.admobnextgenads.R

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun RewardedAdListItem(displayItem: RewardedAdListDisplayItem, modifier: Modifier = Modifier) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = displayItem.image),
                contentDescription = null,
                modifier = Modifier.size(50.dp).clip(RoundedCornerShape(8.dp))
            )

            Column(
                modifier = Modifier.height(50.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                if (displayItem.premium) {
                    Text(displayItem.title, style = MaterialTheme.typography.titleMedium.copy(color = colorResource(R.color.droid_dev_tips_green)))
                } else {
                    Text(displayItem.title, style = MaterialTheme.typography.titleSmall)
                }

                Text(displayItem.description, style = MaterialTheme.typography.bodySmall)
            }
        }

        if (displayItem.premium) {
            Image(
                painter = painterResource(id = R.drawable.lock_icon),
                contentDescription = null,
                modifier = Modifier.size(15.dp),
                colorFilter = ColorFilter.tint(color = if (isSystemInDarkTheme()) Color.White else Color.Black)
            )
        }
    }
}
