package com.droiddevtips.multiscreensupport.common.extensions

import com.droiddevtips.multiscreensupport.R
import com.droiddevtips.multiscreensupport.common.data.Mipmap
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ViewType

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

fun Int.toListDetailItem(viewType: ViewType): ListDetailItem? {

    return when (viewType) {
        ViewType.Follower -> {

            val followers = (50..200).random()

            ListDetailItem(
                title = "Follower $this",
                description = " ${if (followers == 0) "no followers" else if (followers == 1) "${followers} follower" else "${followers} followers"}",
                image = R.drawable.follower,
                viewType = viewType
            )
        }

        ViewType.News -> createNewsItem(viewType = viewType)

        else -> createHomeItem(viewType = viewType)
    }
}

private fun createHomeItem(viewType: ViewType): ListDetailItem? {

    val options = listOf(
        ListDetailItem(
            title = "Getting Started with Android Navigation: A Beginner's Guide to the NavHost",
            description = "Navigating between screens is a fundamental part of any Android application. While historically complex, Google's Navigation Component simplifies this process immensely, with NavHost at its core. This article provides a foundational understanding of the NavHost and its role as the container for your app's navigation. We will walk you through setting up your first navigation graph, defining destinations, and performing basic navigation actions. By the end of this guide, you'll grasp the core concepts of the Navigation Component and be able to implement simple, robust navigation in your Android projects, freeing you to focus on building great features instead of managing Fragment transactions.",
            image = Mipmap.android_navigation,
            viewType = viewType
        ),
        ListDetailItem(
            title = "Mastering NavHost: Advanced Techniques for Dynamic and Modular Navigation",
            description = "While NavHost provides a straightforward API for basic navigation, complex applications require more sophisticated solutions. This article dives deep into advanced NavHost techniques that empower you to build dynamic and scalable navigation structures. We explore topics such as nested navigation graphs for feature encapsulation, integrating dynamic feature modules, and programmatically building navigation graphs at runtime. Furthermore, we will analyze strategies for handling complex deep links and creating custom Navigator instances to manage non-standard destinations like dialogs or custom views. This guide is for experienced developers looking to leverage the full power of the Navigation Component in large-scale, modularized Android applications.",
            image = Mipmap.mastering_navhost,
            viewType = viewType
        ),
        ListDetailItem(
            title = "Declarative Navigation in Jetpack Compose: A Practical Look at NavHost",
            description = "Jetpack Compose has revolutionized UI development on Android, and its navigation paradigm is no exception. This article focuses exclusively on the NavHost composable, the declarative entry point for navigation in a Compose-first application. We will demonstrate how to define a NavHost and its corresponding NavGraph using Kotlin DSL, manage state and back stacks, and pass arguments between composable destinations in a type-safe manner. The discussion will also cover best practices for structuring your navigation logic, handling animations, and integrating ViewModel lifecycles with your composable screens. Join us to learn how NavHost in Jetpack Compose enables you to build fluid, state-driven navigation with significantly less boilerplate.",
            image = Mipmap.declarative_navigation,
            viewType = viewType
        ),
        ListDetailItem(
            title = "Common Pitfalls and Best Practices for Android's NavHost",
            description = "The Android Navigation Component is a powerful tool, but its abstractions can sometimes lead to tricky bugs and unexpected behavior. This article serves as a practical guide to troubleshooting common issues encountered when working with NavHost. We will address prevalent problems such as IllegalStateException: View is not a NavHost, state loss during configuration changes, and challenges with nested graph back stacks. For each issue, we provide clear explanations of the root cause and offer robust solutions. Beyond just fixing bugs, we will distill these lessons into a set of best practices for structuring your navigation graphs, managing argument passing, and testing your navigation logic to create a more stable and maintainable codebase.",
            image = Mipmap.common_pifalls,
            viewType = viewType
        )
    )

    return options.randomOrNull()
}

private fun createNewsItem(viewType: ViewType): ListDetailItem? {

    val options = listOf(
        ListDetailItem(
            title = "Android 15 Officially Released",
            description = "The new Android 15 introduces a \"Private Space\" that allows users to hide sensitive applications in a locked area on their phone. It also brings an AI-powered \"Theft Detection Lock\" that automatically locks the screen if it senses the device has been stolen. For larger screens like foldables and tablets, users can now pin and unpin the taskbar to customize their home screen. Other improvements include partial screen recording, satellite connectivity for messaging, and enhanced camera controls for low-light situations. While many features are available across Android 15 devices, some, like satellite messaging, require specific hardware found in devices like the Pixel 9 series and carrier support.",
            image = Mipmap.android_15,
            viewType = viewType
        ),
        ListDetailItem(
            title = "Google's Pixel 9 Series Expands with New Models",
            description = "The Pixel 9 is rumored to feature a 6.24-inch OLED display, a Tensor G4 chipset, and 12GB of RAM. The Pro models are expected to come with 16GB of RAM and a triple-lens camera system, including a telephoto lens. The new Pixel 9 Pro Fold is anticipated to have a larger and slimmer design compared to its predecessor. All models will likely run on the new Tensor G4 chip and may include an ultrasonic fingerprint sensor and satellite connectivity for SOS functions.",
            image = Mipmap.google_pixel9,
            viewType = viewType
        ),
        ListDetailItem(
            title = "Android Auto Undergoes Changes, Including Removal of Mini-Games",
            description = "The GameSnacks feature has been disappearing from the Android Auto menu for many users, particularly those in the beta program, leading to speculation that Google is intentionally phasing it out. This move aligns with Google's broader effort to simplify the Android Auto interface and reduce distractions. In addition to this change, Google is working on fixes for connectivity issues with Pixel phones and plans to integrate AI-powered features like Call Screen and Call Notes from Gemini. Future updates are also expected to bring more personalization options, such as accent colors based on the phone's wallpaper.",
            image = Mipmap.android_auto,
            viewType = viewType
        ),
        ListDetailItem(
            title = "Anticipation Builds for a New Wave of Android Phones in 2025",
            description = "Samsung is expected to launch the Galaxy S25 series, with the S25 Ultra featuring a new 50MP ultra-wide camera and a titanium design. The OnePlus 13 is positioned as a top contender with a strong combination of high-end specs and competitive pricing. Google is also set to release its Pixel 10 series later in the year. The foldable market continues to grow, with the Samsung Galaxy Z Fold 7 and Z Flip 7 anticipated to offer thinner, lighter designs with improved durability. Additionally, a range of new mid-range and budget-friendly options, such as the Google Pixel 9a, are also expected to hit the market.",
            image = Mipmap.galaxy_25,
            viewType = viewType
        )
    )

    return options.randomOrNull()
}
