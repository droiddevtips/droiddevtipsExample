package com.droiddevtips.visibilitychanged.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.NavigationBarItem
import com.droiddevtips.visibilitychanged.data.TabRoute

@Composable
fun RowScope.CustomNavigationBarItem(
    tabItem: TabRoute,
    index: Int,
    selectedTab: Int,
    modifier: Modifier = Modifier,
    onItemSelected: () -> Unit
) {
    NavigationBarItem(
        modifier = modifier,
        selected = selectedTab == index,
        label = {
            Text(tabItem.title)
        },
        icon = {
            Icon(
                painter = painterResource(id = tabItem.icon),
                contentDescription = null
            )
        },
        onClick = onItemSelected
    )
}