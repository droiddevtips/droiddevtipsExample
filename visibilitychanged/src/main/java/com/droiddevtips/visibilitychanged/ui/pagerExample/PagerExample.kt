package com.droiddevtips.visibilitychanged.ui.pagerExample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onVisibilityChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.droiddevtips.visibilitychanged.R
import com.droiddevtips.visibilitychanged.data.AppString
import com.droiddevtips.visibilitychanged.data.TabRoute

/**
 * A [HorizontalPager] wrapper that emits visibility events for the
 * currently settled page.
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2026. All rights reserved.
 */
@Composable
fun PagerExample(
    modifier: Modifier = Modifier,
    onTabSelected: (String) -> Unit
) {

    val visiblePageIndex = rememberSaveable { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { 3 })

    Box(modifier = modifier.fillMaxSize()) {

        HorizontalPager(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .onVisibilityChanged { visible ->
                    if (visible) {
                        onTabSelected(TabRoute.Pager.title)
                    }
                }, state = pagerState
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .onVisibilityChanged {
                        visiblePageIndex.intValue = page
                    }
            ) {
                Text(
                    text = stringResource(id = AppString.page_visible,(page + 1)),
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
            }
        }

        Text(
            text = stringResource(id = AppString.swipe_between_pages),
            modifier = Modifier.align(alignment = Alignment.TopCenter)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
                .align(alignment = Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = AppString.page_nr_visible,(visiblePageIndex.intValue + 1)),
                modifier = Modifier.padding(all = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}