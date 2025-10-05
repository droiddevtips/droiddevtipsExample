package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.article.ui.list.ArticleListView
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.data.HomeArticleViewState
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.data.HomeViewEvent

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun HomeTabletLandscapeView(
    viewState: State<HomeArticleViewState>,
    modifier: Modifier = Modifier,
    event: (HomeViewEvent) -> Unit
) {

//    val density = LocalDensity.current
//    val scrollable = rememberScrollState()

    Row(modifier = modifier.fillMaxSize()) {


        ArticleListView(
            viewState = viewState,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.35f)
                .fillMaxHeight(),
            event = event)

        /*
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = 0.35f)
                .verticalScroll(scrollable),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(1.dp))

            for (i in 1..50) {
                ArticleListItem(onItemClicked = {

                })
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
        */

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
            ) {
                HomeDetailView()
            }
        }
    }
}