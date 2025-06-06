package com.droiddevtips.admobnextgenads

import android.app.Application
import com.droiddevtips.admobnextgenads.common.ads.MobileAdsManager

/**
 * The application class
 * Created by Melchior Vrolijk
 * Copyright (c) 2025. All rights reserved.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        MobileAdsManager.init(context = applicationContext)
    }
}