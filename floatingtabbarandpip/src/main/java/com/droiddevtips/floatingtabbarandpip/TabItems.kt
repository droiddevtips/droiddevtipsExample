package com.droiddevtips.floatingtabbarandpip

import android.icu.text.CaseMap
import androidx.compose.runtime.Composable
import com.droiddevtips.floatingtabbarandpip.favorites.FavoriteView
import com.droiddevtips.floatingtabbarandpip.profile.ProfileView
import com.droiddevtips.floatingtabbarandpip.videos.VideosView

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
data class TabItem(val tabIcon: Int, val title: String)

val items = listOf(
    TabItem(tabIcon = R.drawable.video_icon, title = "Videos"),
    TabItem(tabIcon = R.drawable.favorite_icon, title = "Favorite"),
    TabItem(tabIcon = R.drawable.profile_icon, title = "Profile"),
)