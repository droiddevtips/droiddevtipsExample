package com.droiddevtips.admobnextgenads.common.ads.bannerAd

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.droiddevtips.admobnextgenads.common.ads.MobileAdsManager
import com.droiddevtips.admobnextgenads.common.ads.NextGenAdUnit
import com.droiddevtips.admobnextgenads.common.ads.bannerAd.fetcher.CollapsibleType
import com.droiddevtips.admobnextgenads.common.adsLoadingView.AdLoadingView
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdEventCallback
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRefreshCallback
import com.google.android.libraries.ads.mobile.sdk.common.FullScreenContentError
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError

/**
 * This is the Collapsible banner ad composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun CollapsibleBannerAdView(
    modifier: Modifier = Modifier,
    bannerAdUnit: NextGenAdUnit,
    collapsibleType: CollapsibleType
) {

    val activity = LocalActivity.current
    val isInPreview = LocalInspectionMode.current

    if (activity == null || isInPreview) {
        BannerAdViewFailedPlaceHolder()
        return
    }

    val initialViewHeight = remember {
        mutableStateOf(300.dp)
    }

    if (initialViewHeight.value == 0.dp)
        return

    val loadingViewHeight = remember {
        mutableStateOf(300.dp)
    }

    val isLoadingAds = remember { mutableStateOf(true) }
    var parentView by remember { mutableStateOf<FrameLayout?>(null) }
    var banner_Ad_View by remember { mutableStateOf<View?>(null) }
    var banner_Ad by remember { mutableStateOf<BannerAd?>(null) }

    when (collapsibleType) {

        CollapsibleType.TOP -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .animateContentSize()
            ) {

                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    update = {
                        it.requestLayout()
                    },
                    factory = { context ->
                        FrameLayout(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                        }.also { adLayout ->
                            if (!isInPreview) {
                                parentView = adLayout
                                MobileAdsManager.fetchCollapsibleBannerAd(
                                    context = activity,
                                    collapsibleType = collapsibleType,
                                    adUnit = bannerAdUnit
                                ) { bannerAdView ->

                                    if (bannerAdView?.isCollapsible() != true)
                                        return@fetchCollapsibleBannerAd

                                    isLoadingAds.value = false

                                    bannerAdView.apply {
                                        banner_Ad = this
                                        initialViewHeight.value = getAdSize().height.dp

                                        // Banner Ad refresh callback
                                        bannerAdRefreshCallback = object : BannerAdRefreshCallback {

                                            override fun onAdRefreshed() {
                                                super.onAdRefreshed()
                                                println("[Banner Ad View] - onAdRefreshed")
                                            }

                                            override fun onAdFailedToRefresh(adError: LoadAdError) {
                                                super.onAdFailedToRefresh(adError)
                                                println("[Banner Ad View] - Ad Failed to refresh, cause: ${adError.message}")
                                            }
                                        }

                                        adEventCallback = object : BannerAdEventCallback {

                                            override fun onAdShowedFullScreenContent() {
                                                super.onAdShowedFullScreenContent()
                                                println("[Banner Ad View] - onAdShowedFullScreenContent")
                                            }

                                            override fun onAdDismissedFullScreenContent() {
                                                super.onAdDismissedFullScreenContent()
                                                println("[Banner Ad View] - onAdDismissedFullScreenContent")
                                            }

                                            override fun onAdFailedToShowFullScreenContent(
                                                fullScreenContentError: FullScreenContentError
                                            ) {
                                                super.onAdFailedToShowFullScreenContent(
                                                    fullScreenContentError
                                                )
                                                println("[Banner Ad View] - onAdFailedToShowFullScreenContent")
                                            }

                                            override fun onAdImpression() {
                                                super.onAdImpression()
                                                println("[Banner Ad View] - onAdImpression")
                                            }

                                            override fun onAdClicked() {
                                                super.onAdClicked()
                                                println("[Banner Ad View] - onAdClicked")
                                            }
                                        }

                                        activity.runOnUiThread {

                                            this.getView(activity).also { bannerAdView ->

                                                println("[Banner Ad View Parent] - Banner ads with unit ID: ${bannerAdUnit.id} - parent: ${bannerAdView.parent}")

                                                try {

                                                    if (bannerAdView.parent != null)
                                                        (bannerAdView.parent as ViewGroup).removeView(
                                                            bannerAdView
                                                        )

                                                    adLayout.addView(bannerAdView)

                                                    banner_Ad_View = bannerAdView

                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                    initialViewHeight.value = 0.dp
                                                    println("[Banner Ad View Parent] [Exception] - Error loading banner ads, cause: ${e.message}")
                                                } catch (e: Throwable) {
                                                    e.printStackTrace()
                                                    initialViewHeight.value = 0.dp
                                                    println("[Banner Ad View Parent] [Throwable] - Error loading banner ads, cause: ${e.message}")
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                )

                AdLoadingView(
                    visible = isLoadingAds.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(loadingViewHeight.value)
                )
            }
        }

        CollapsibleType.BOTTOM -> {

            val applySystemBarPadding = remember { mutableStateOf(true) }

            ConstraintLayout(modifier = Modifier
                .fillMaxSize()
                .then(
                    if (applySystemBarPadding.value) {
                        Modifier.systemBarsPadding()
                    } else {
                        Modifier
                    }
                )) {

                val (bannerAdFrame, loadingView) = createRefs()

                AndroidView(
                    modifier = Modifier
                        .constrainAs(bannerAdFrame) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                            width = Dimension.fillToConstraints
                        }
                        .fillMaxWidth()
                        .animateContentSize(),
                    update = {
                        it.requestLayout()
                    },
                    factory = { context ->
                        FrameLayout(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                        }.also { adLayout ->
                            MobileAdsManager.fetchCollapsibleBannerAd(
                                context = context,
                                collapsibleType = CollapsibleType.BOTTOM,
                                adUnit = bannerAdUnit
                            ) { bannerAdView ->

                                if (bannerAdView?.isCollapsible() != true)
                                    return@fetchCollapsibleBannerAd

                                isLoadingAds.value = false

                                bannerAdView.apply {
                                    banner_Ad = this
                                    initialViewHeight.value = getAdSize().height.dp

                                    // Banner Ad refresh callback
                                    bannerAdRefreshCallback = object : BannerAdRefreshCallback {

                                        override fun onAdRefreshed() {
                                            super.onAdRefreshed()
                                            println("[Banner Ad View] - onAdRefreshed")
                                        }

                                        override fun onAdFailedToRefresh(adError: LoadAdError) {
                                            super.onAdFailedToRefresh(adError)
                                            println("[Banner Ad View] - Ad Failed to refresh, cause: ${adError.message}")
                                        }
                                    }

                                    adEventCallback = object : BannerAdEventCallback {

                                        override fun onAdShowedFullScreenContent() {
                                            super.onAdShowedFullScreenContent()
                                            println("[Banner Ad View] - onAdShowedFullScreenContent")
                                        }

                                        override fun onAdDismissedFullScreenContent() {
                                            super.onAdDismissedFullScreenContent()
                                            applySystemBarPadding.value = false
                                            println("[Banner Ad View] - onAdDismissedFullScreenContent")
                                        }

                                        override fun onAdFailedToShowFullScreenContent(
                                            fullScreenContentError: FullScreenContentError
                                        ) {
                                            super.onAdFailedToShowFullScreenContent(
                                                fullScreenContentError
                                            )
                                            println("[Banner Ad View] - onAdFailedToShowFullScreenContent")
                                        }

                                        override fun onAdImpression() {
                                            super.onAdImpression()
                                            println("[Banner Ad View] - onAdImpression")
                                        }

                                        override fun onAdClicked() {
                                            super.onAdClicked()
                                            println("[Banner Ad View] - onAdClicked")
                                        }
                                    }

                                    activity.runOnUiThread {

                                        this.getView(activity).also { bannerAdView ->

                                            println("[Banner Ad View Parent] - Banner ads with unit ID: ${bannerAdUnit.id} - parent: ${bannerAdView.parent}")

                                            try {

                                                if (bannerAdView.parent != null)
                                                    (bannerAdView.parent as ViewGroup).removeView(
                                                        bannerAdView
                                                    )

                                                adLayout.addView(bannerAdView)

                                                banner_Ad_View = bannerAdView

                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                                initialViewHeight.value = 0.dp
                                                println("[Banner Ad View Parent] [Exception] - Error loading banner ads, cause: ${e.message}")
                                            } catch (e: Throwable) {
                                                e.printStackTrace()
                                                initialViewHeight.value = 0.dp
                                                println("[Banner Ad View Parent] [Throwable] - Error loading banner ads, cause: ${e.message}")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                )

                AdLoadingView(
                    visible = isLoadingAds.value,
                    frameModifier = Modifier
                        .constrainAs(loadingView) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                            start.linkTo(parent.start)
                            width = Dimension.fillToConstraints
                        },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(loadingViewHeight.value)
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            if (parentView != null && banner_Ad_View != null) {
                banner_Ad_View?.let {
                    (it.parent as? ViewGroup)?.removeView(it)
                }
                parentView?.removeAllViews()
            }
            banner_Ad?.destroy()
        }
    }
}

@Composable
private fun BannerAdViewFailedPlaceHolder(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(color = Color.LightGray)
    ) {
        Text(
            "Failed to load banner Ad View", color = Color.Black, modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
}