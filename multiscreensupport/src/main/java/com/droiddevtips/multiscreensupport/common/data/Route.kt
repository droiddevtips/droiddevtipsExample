package com.droiddevtips.multiscreensupport.common.data

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
sealed class MultiScreenSupportRoute(val route: String) {

    data object Home : MultiScreenSupportRoute("home")
    data object RememberExample : MultiScreenSupportRoute("remember")
    data object RememberSaveableExample : MultiScreenSupportRoute("remember_saveable")
    data object ViewModelFactoryExample : MultiScreenSupportRoute("viewmodel_factory")

}