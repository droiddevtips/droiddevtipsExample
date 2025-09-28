package com.droiddevtips.multiscreensupport.domain

import androidx.navigation.NavController

/**
 * These are the [NavHost] controller
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
fun NavController.navigateToView(route:String) {
    try {
        navigate(route = route)
    }catch (e: Exception) {
        e.printStackTrace()
    }
}