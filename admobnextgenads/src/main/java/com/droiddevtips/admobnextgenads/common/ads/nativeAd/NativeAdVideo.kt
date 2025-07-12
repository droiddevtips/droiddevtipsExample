package com.droiddevtips.admobnextgenads.common.ads.nativeAd

import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.droiddevtips.admobnextgenads.R
import com.droiddevtips.admobnextgenads.common.ads.MobileAdsManager
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.droiddevtips.admobnextgenads.common.ads.adsErrorView.AdErrorView
import com.droiddevtips.admobnextgenads.common.ads.nativeAd.elements.NativeAdTextElement
import com.droiddevtips.admobnextgenads.common.adsLoadingView.AdLoadingView
import com.droiddevtips.typography.extensions.dpToPx
import com.google.android.libraries.ads.mobile.sdk.nativead.MediaView
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAd
import com.google.android.libraries.ads.mobile.sdk.nativead.NativeAdView

/**
 * The native ad video composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun NativeAdVideoComposeView(
    adUnit: NextGenAdUnit,
    mutedVideo: Boolean
) {

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
    val errorLoadingAds = remember { mutableStateOf(false) }
    var native_Ad by remember { mutableStateOf<NativeAd?>(null) }

    Box(
        modifier = Modifier
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
            NativeAdContainerView(nativeAd = native_Ad)
        }

        LaunchedEffect(Unit) {

            MobileAdsManager.fetchNativeVideoAd(
                adUnit = adUnit,
                muteVideo = mutedVideo
            ) { nativeVideoAd, error ->
                errorLoadingAds.value = error
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

        AdErrorView(
            visible = errorLoadingAds.value,
            modifier = Modifier.fillMaxWidth()
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
fun NativeAdContainerView(modifier: Modifier = Modifier, nativeAd: NativeAd? = null) {

    AndroidView(
        modifier = modifier
            .fillMaxWidth(),
        update = {
            it.requestLayout()
        },
        factory = { context ->

            NativeAdView(context = context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }.also { nativeAdView ->

                attachNativeAdComposableView(containerView = nativeAdView) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {

                        NativeAdTopSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 2.dp)
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .padding(top = 8.dp)
                        ) {

                            Column(modifier = Modifier.fillMaxWidth()) {

                                NativeAdTitleSection(
                                    modifier = Modifier.fillMaxWidth(),
                                    nativeAd = nativeAd,
                                    nativeAdView = nativeAdView
                                )

                                NativeAdBody(nativeAd = nativeAd, nativeAdView = nativeAdView)
                            }

                            NativeAdMediaView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                nativeAd = nativeAd,
                                nativeAdView = nativeAdView
                            )

                            NativeAdActionSection(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp)
                                    .padding(top = 8.dp),
                                nativeAd = nativeAd,
                                nativeAdView = nativeAdView
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun NativeAdTopSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NativeAdTextElement()
    }
}

@Composable
private fun NativeAdTitleSection(
    modifier: Modifier = Modifier,
    nativeAd: NativeAd?,
    nativeAdView: NativeAdView
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        nativeAd?.icon?.drawable?.let { _iconDrawable ->

            val iconSize = 30.dpToPx(context = LocalContext.current)

            AndroidView(
                modifier = Modifier.padding(all = 5.dp),
                update = {},
                factory = { context ->
                    val iconView = ImageView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            iconSize.toInt(),
                            iconSize.toInt()
                        )
                    }.also { imageView ->
                        imageView.setImageDrawable(_iconDrawable)
                    }
                    nativeAdView.iconView = iconView
                    iconView
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            nativeAd?.headline?.let { headline ->
                AndroidView(factory = { context ->
                    val headlineView = ComposeView(context).apply {
                        setContent {
                            Text(
                                headline,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 12.sp
                            )
                        }
                    }
                    nativeAdView.headlineView = headlineView
                    headlineView
                })
            }

            nativeAd?.advertiser?.let { advertiser ->

                AndroidView(factory = { context ->

                    ComposeView(context = context).apply {
                        setContent {
                            Text(
                                advertiser,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 12.sp
                            )
                        }
                    }.also {
                        nativeAdView.advertiserView = it
                    }
                })
            }

            nativeAd?.starRating?.let {
                Text(
                    "Rating: $it",
                    fontSize = 10.sp,
                    lineHeight = 10.sp
                )
            }
        }
    }
}

@Composable
fun NativeAdBody(modifier: Modifier = Modifier, nativeAd: NativeAd?, nativeAdView: NativeAdView) {

    nativeAd?.body?.let { body ->

        AndroidView(modifier = modifier, factory = { context ->

            ComposeView(context = context).apply {
                setContent {
                    Text(
                        body,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(end = 20.dp),
                        lineHeight = 10.sp
                    )
                }
            }.also {
                nativeAdView.bodyView = it
            }
        })
    }

}

@Composable
fun NativeAdMediaView(
    modifier: Modifier = Modifier,
    nativeAd: NativeAd?,
    nativeAdView: NativeAdView
) {

    val mediaViewHeight = 175.dpToPx(context = LocalContext.current)
    val mediaViewWidth = 250.dpToPx(context = LocalContext.current)

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        AndroidView(
            modifier = modifier,
            update = {},
            factory = { context ->

                FrameLayout(context).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }.also { frameLayout ->
                    frameLayout.addView(
                        MediaView(nativeAdView.context).apply {
                            layoutParams = FrameLayout.LayoutParams(
                                mediaViewWidth.toInt(),
                                mediaViewHeight.toInt()
                            )
                            (layoutParams as FrameLayout.LayoutParams).gravity =
                                Gravity.CENTER_HORIZONTAL
                        }.also { media_view ->
                            if (nativeAd != null) {
                                nativeAdView.registerNativeAd(nativeAd, media_view)
                            }
                        }
                    )
                }
            }
        )
    }
}

@Composable
private fun NativeAdActionSection(
    modifier: Modifier = Modifier,
    nativeAd: NativeAd?,
    nativeAdView: NativeAdView
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {

        nativeAd?.apply {

            price?.let { price ->
                AndroidView(factory = { context ->

                    ComposeView(context = context).apply {
                        setContent {
                            Text(
                                price,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(all = 5.dp)
                            )
                        }
                    }.also {
                        nativeAdView.priceView = it
                    }
                })
            }

            store?.let { store ->
                AndroidView(factory = { context ->

                    ComposeView(context = context).apply {
                        setContent {
                            Text(
                                store,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(all = 5.dp)
                            )
                        }
                    }.also {
                        nativeAdView.storeView = it
                    }
                })
            }

            callToAction?.let { cta ->

                AndroidView(factory = { context ->

                    Button(context).apply {
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        text = cta
                        setTextColor(android.graphics.Color.WHITE)
                        typeface = ResourcesCompat.getFont(
                            context,
                            com.droiddevtips.typography.R.font.roboto_medium
                        )

                        val backgroundDrawable = GradientDrawable().apply {
                            shape = GradientDrawable.RECTANGLE
                            cornerRadius = 52f
                            setColor(context.getColor(R.color.droid_dev_tips_green))
                        }
                        background = backgroundDrawable
                    }.also {
                        nativeAdView.callToActionView = it
                    }
                })
            }
        }
    }
}

@Preview
@Composable
fun VideoControllerView(modifier: Modifier = Modifier) {

    Box {

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.align(alignment = Alignment.BottomStart)
        ) {

            Image(
                painter = painterResource(id = R.drawable.play_icon),
                modifier = Modifier.size(32.dp),
                colorFilter = if (isSystemInDarkTheme()) ColorFilter.tint(color = Color.White) else ColorFilter.tint(
                    color = Color.Black
                ),
                contentDescription = stringResource(id = R.string.play_video_ad_description)
            )
            Image(
                painter = painterResource(id = R.drawable.mute_icon),
                modifier = Modifier.size(32.dp),
                colorFilter = if (isSystemInDarkTheme()) ColorFilter.tint(color = Color.White) else ColorFilter.tint(
                    color = Color.Black
                ),
                contentDescription = stringResource(id = R.string.mute_video_ad_description)
            )
        }
    }
}

private fun attachNativeAdComposableView(
    containerView: NativeAdView,
    content: @Composable () -> Unit
) {

    val nativeAdComposeView = ComposeView(containerView.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
    nativeAdComposeView.setContent(content)
    containerView.addView(nativeAdComposeView)
}
