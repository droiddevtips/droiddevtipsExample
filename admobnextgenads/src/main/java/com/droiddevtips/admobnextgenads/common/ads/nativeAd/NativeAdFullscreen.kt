package com.droiddevtips.admobnextgenads.common.ads.nativeAd

import android.graphics.drawable.GradientDrawable
import android.view.ViewGroup
import android.widget.Button
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil3.compose.AsyncImage
import com.droiddevtips.admobnextgenads.common.ads.MobileAdsManager
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.droiddevtips.admobnextgenads.common.adsLoadingView.AdLoadingView
import com.google.android.libraries.ads.mobile.sdk.nativead.MediaView
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdView

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun NativeAdFullscreenView(adUnit: NextGenAdUnit, modifier: Modifier = Modifier) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val isInPreview = LocalInspectionMode.current

    val showNativeAd = remember {
        mutableStateOf(true)
    }

    if (!showNativeAd.value)
        return

    val loadingViewHeight = remember {
        mutableStateOf(300.dp)
    }

    val isLoadingAds = remember { mutableStateOf(true) }
    var native_Ad by remember { mutableStateOf<NativeAd?>(null) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {

        if (isInPreview) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(color = Color.LightGray)
            )

            return
        }

        if (native_Ad != null) {
            FullScreenNativeAdContainer(nativeAd = native_Ad)
        }

        LaunchedEffect(Unit) {

            MobileAdsManager.fetchNativeFullscreenAd(
                adUnit = adUnit
            ) { nativeVideoAd ->
                isLoadingAds.value = false
                native_Ad = nativeVideoAd
            }
        }

        AdLoadingView(
            visible = isLoadingAds.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(loadingViewHeight.value)
        )
    }

    DisposableEffect(lifecycleOwner, effect = {

        val observer = LifecycleEventObserver { _, event ->

            if (event == Lifecycle.Event.ON_ANY) {
                native_Ad?.destroy()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

}

@Composable
private fun FullScreenNativeAdContainer(nativeAd: NativeAd?) {

    if (nativeAd == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(300.dp)
                .background(color = Color.LightGray)
        )
        return
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        update = {
            it.requestLayout()
        },
        factory = { context ->

            NativeAdView(context = context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }.also { nativeAdView ->

                val composeView = ComposeView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    setContent {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .heightIn(min = 50.dp)
                                .background(color = Color.Black.copy(alpha = 0.8f))
                        ) {

                            AndroidView(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(alignment = Alignment.Center),
                                factory = { context ->
                                    MediaView(context).apply {
                                        layoutParams = ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.MATCH_PARENT
                                        )
                                    }.also { mediaView ->
                                        nativeAdView.registerNativeAd(
                                            nativeAd,
                                            mediaView = mediaView
                                        )
                                    }
                                }
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(alignment = Alignment.BottomCenter)
                                    .padding(horizontal = 16.dp)
                                    .clip(shape = RoundedCornerShape(25.dp))
                                    .background(color = Color.White)
                            ) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(all = 24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {

                                    nativeAd.icon?.uri?.let { _icon_uri ->
                                        AsyncImage(
                                            model = _icon_uri,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(65.dp)
                                                .clip(shape = CircleShape)
                                                .background(color = Color.Red)
                                        )
                                    }

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier.background(
                                                color = Color(
                                                    0xFFFFCC66
                                                )
                                            )
                                        ) {
                                            Text(
                                                "Ad",
                                                fontSize = 10.sp,
                                                color = Color.White,
                                                lineHeight = 10.sp,
                                                textAlign = TextAlign.Center
                                            )
                                        }

                                        AndroidView(factory = { context ->

                                            val headlineView = ComposeView(context).apply {
                                                layoutParams = ViewGroup.LayoutParams(
                                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                                )
                                                setContent {
                                                    Text(
                                                        text = nativeAd.headline ?: "",
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.Black,
                                                        fontSize = 16.sp
                                                    )
                                                }
                                            }

                                            nativeAdView.headlineView = headlineView
                                            headlineView
                                        })
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {

                                        nativeAd.adChoicesInfo?.let {

                                            if (it.text.isNotBlank()) {
                                                Text(it.text, color = Color.Black)
                                            }

                                            it.images.forEach {
                                                AsyncImage(
                                                    model = it.uri,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    }

                                    Text(text = nativeAd.body ?: "", color = Color.Black)

                                    AndroidView(factory = { context ->

                                        Button(context).apply {
                                            layoutParams = ViewGroup.LayoutParams(
                                                800,
                                                150
                                            )
                                            setTextColor(android.graphics.Color.WHITE)
                                            val backgroundDrawable = GradientDrawable().apply {
                                                shape = GradientDrawable.RECTANGLE
                                                cornerRadius = 52f
                                                setColor(context.getColor(com.droiddevtips.admobnextgenads.R.color.droid_dev_tips_green))
                                            }
                                            background = backgroundDrawable
                                            text = nativeAd.callToAction
                                        }.also {
                                            nativeAdView.callToActionView = it
                                        }
                                    })
                                }
                            }

                            AndroidView(factory = { context ->

                                val advertiserView = ComposeView(context).apply {
                                    setContent {
                                        nativeAd.advertiser?.let {
                                            Box(
                                                modifier = Modifier
                                                    .background(
                                                        shape = RoundedCornerShape(
                                                            4.dp
                                                        ), color = Color.Red
                                                    )
                                                    .align(Alignment.TopStart)
                                            ) {
                                                Text(it, modifier = Modifier.padding(all = 4.dp))
                                            }
                                        }
                                    }
                                }
                                nativeAdView.advertiserView = advertiserView
                                advertiserView
                            })
                        }
                    }
                }

                nativeAdView.addView(composeView)
            }
        }
    )
}