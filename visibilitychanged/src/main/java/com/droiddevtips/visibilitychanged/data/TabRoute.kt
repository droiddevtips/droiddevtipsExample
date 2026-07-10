package com.droiddevtips.visibilitychanged.data

import com.droiddevtips.visibilitychanged.R

sealed class TabRoute (val route:String, val title:String, val icon: Int) {
    data object List: TabRoute(route = "list", title = "List", icon = R.drawable.list)
    data object Pager: TabRoute(route = "pager", title = "Pager", icon = R.drawable.pager)
}