package com.droiddevtips.floatingtabbarandpip.common.videoPlayer.ui.metaData

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoDetails
import com.droiddevtips.floatingtabbarandpip.extensions.shimmerEffect

/**
 * The video meta data view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun MetaDataView(
    metaData: VideoDetails?,
    modifier: Modifier = Modifier
) {

    if (metaData == null)
        return

    Column(modifier = modifier
        .padding(vertical = 18.dp)
        .verticalScroll(rememberScrollState())
    ) {
        Box {
            Text(
                metaData.title ?: "-",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                )
            )

            Column {
                AnimatedVisibility(
                    visible = metaData.title == null, enter = fadeIn(), exit = fadeOut(
                        tween(500)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                            .background(shape = RoundedCornerShape(8.dp), color = Color.LightGray)
                            .shimmerEffect()
                    )
                }
            }
        }

        Box {

            Text(
                metaData.authorName ?: "-",
                style = MaterialTheme.typography.titleSmall,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black
            )

            Column {
                AnimatedVisibility(
                    visible = metaData.authorName == null, enter = fadeIn(), exit = fadeOut(
                        tween(500)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                            .background(shape = RoundedCornerShape(8.dp), color = Color.LightGray)
                            .shimmerEffect()
                    )
                }
            }
        }

        Text(
            text = "Source: YouTube",
            style = MaterialTheme.typography.titleSmall,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
    }
}