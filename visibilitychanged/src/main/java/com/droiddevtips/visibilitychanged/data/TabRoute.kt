package com.droiddevtips.visibilitychanged.data

import com.droiddevtips.visibilitychanged.R

/**
 * Represents the set of top-level tab destinations hosted within the app's
 * bottom navigation / tab bar and driven by a single [NavHost].
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
sealed class TabRoute (val route:String, val title:String, val icon: Int) {
    data object List: TabRoute(route = "list", title = "List", icon = R.drawable.list)
    data object Pager: TabRoute(route = "pager", title = "Pager", icon = R.drawable.pager)
}