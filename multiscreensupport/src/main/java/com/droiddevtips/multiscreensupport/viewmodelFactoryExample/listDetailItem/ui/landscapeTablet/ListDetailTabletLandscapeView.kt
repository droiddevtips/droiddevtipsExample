package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.landscapeTablet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailEvent
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailViewState
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.detail.DetailView
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.list.ListView

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun ListDetailTabletLandscapeView(
    viewState: State<ListDetailViewState>,
    modifier: Modifier = Modifier,
    event: (ListDetailEvent) -> Unit
) {
    Row(modifier = modifier) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = 0.35f)
        ) {
            ListView(
                viewState = viewState,
                modifier = modifier,
                event = event
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = 0.7f)
                .weight(1f)
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
                DetailView(listDetailItem = viewState.value.selectedItem)
            }
        }
    }
}