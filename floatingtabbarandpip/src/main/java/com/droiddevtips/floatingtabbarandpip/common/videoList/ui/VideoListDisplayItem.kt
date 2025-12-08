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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.droiddevtips.appwindowsizeandorientationdetector.Device
import com.droiddevtips.appwindowsizeandorientationdetector.deviceDetectorCurrentWindowSize
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoItem
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.VideoListAction
import com.droiddevtips.floatingtabbarandpip.extensions.asVideoItem
import com.droiddevtips.floatingtabbarandpip.extensions.shimmerEffect
import com.droiddevtips.floatingtabbarandpip.util.AppDrawable
import com.droiddevtips.floatingtabbarandpip.util.AppString

/**
 * The video display item composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun VideoListDisplayItem(
    isPlaying: Boolean = false,
    item: VideoItem,
    modifier: Modifier = Modifier,
    action: (VideoListAction) -> Unit
) {

    val windowSize = deviceDetectorCurrentWindowSize()
    val showLoadingShimmer = rememberSaveable { mutableStateOf(true) }
    val showFailedToLoadImage = rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .then(
                    if (isPlaying) {
                        Modifier.blur(radiusX = 45.dp, radiusY = 45.dp)
                    } else {
                        Modifier
                    }
                ),
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

        if (isPlaying) {
            Text(
                text = stringResource(id = AppString.playing),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        } else {
            IconButton(
                modifier = Modifier
                    .size(if (windowSize.device == Device.Mobile) 60.dp else 80.dp)
                    .align(alignment = Alignment.Center),
                onClick = {
                    action(VideoListAction.LaunchYouTubePlayer(videoID = item.id))
                }
            ) {
                Image(
                    painter = painterResource(id = AppDrawable.youtube),
                    contentDescription = null,
                    modifier = Modifier
                        .size(if (windowSize.device == Device.Mobile) 60.dp else 80.dp)
                )
            }
        }

        if (item.favorite && !isPlaying) {
            Image(
                painter = painterResource(id = AppDrawable.favorite_icon),
                contentDescription = null,
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(all = 15.dp)
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
                    painter = painterResource(id = AppDrawable.image_icon),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
fun TestVideoListDisplayItem() {

    val favoriteItem = "-GikklXjkgM".asVideoItem(favorite = true)
    val videoItem = "-GikklXjkgM".asVideoItem(favorite = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        VideoListDisplayItem(item = favoriteItem, action = {})

        VideoListDisplayItem(item = videoItem, action = {})

        VideoListDisplayItem(isPlaying = true, item = favoriteItem, action = {})

        VideoListDisplayItem(isPlaying = true, item = videoItem, action = {})

    }
}