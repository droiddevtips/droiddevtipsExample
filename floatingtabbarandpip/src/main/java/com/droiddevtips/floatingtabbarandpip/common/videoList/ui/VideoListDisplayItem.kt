package com.droiddevtips.floatingtabbarandpip.common.videoList.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.droiddevtips.floatingtabbarandpip.R
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoItem
import com.droiddevtips.floatingtabbarandpip.extensions.shimmerEffect

/**
 * The video display item composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun VideoListDisplayItem(
    item: VideoItem,
    modifier: Modifier = Modifier
) {

    val showLoadingShimmer = rememberSaveable { mutableStateOf(true) }
    val showFailedToLoadImage = rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {

        AsyncImage(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            model = item.videoThumbnailUrl,
            onState = { state ->

                when (state) {

                    is AsyncImagePainter.State.Loading -> {
                        showFailedToLoadImage.value = false
                        showLoadingShimmer.value = true
                    }

                    is AsyncImagePainter.State.Success -> {
                        showLoadingShimmer.value = false
                    }

                    is AsyncImagePainter.State.Error -> {
                        showFailedToLoadImage.value = true
                        showLoadingShimmer.value = false
                    }

                    else -> Unit
                }
            },
            contentDescription = null
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.Black.copy(alpha = 0.35f))
        )

        IconButton(
            modifier = Modifier.align(alignment = Alignment.Center),
            onClick = {}
        ) {
            Image(
                painter = painterResource(id = R.drawable.youtube),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
            )
        }

        if (item.favorite) {
            Image(
                painter = painterResource(id = R.drawable.favorite_icon),
                contentDescription = null,
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(all = 10.dp)
                    .size(20.dp)
            )
        }

        AnimatedVisibility(
            visible = showLoadingShimmer.value,
            enter = fadeIn(),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .shimmerEffect()
            )
        }

        AnimatedVisibility(
            visible = showFailedToLoadImage.value,
            enter = fadeIn(),
            exit = fadeOut(animationSpec = tween(500))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(id = R.drawable.image_icon),
                    contentDescription = null
                )
            }
        }
    }
}