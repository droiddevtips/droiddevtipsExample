package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ViewType
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.emptyView.NoItemSelected

/**
 * The generic detail composable view
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun DetailView(listDetailItem: ListDetailItem?, modifier: Modifier = Modifier) {

    if (listDetailItem == null) {
        NoItemSelected(modifier = Modifier.fillMaxSize())
        return
    }

    val scrollable = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 30.dp)
            .padding(top = 16.dp)
            .verticalScroll(scrollable),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {

        if (listDetailItem.viewType == ViewType.Follower) {
            Image(
                painter = painterResource(id = listDetailItem.image),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                contentDescription = null
            )
        } else {
            Image(
                painter = painterResource(id = listDetailItem.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = listDetailItem.title,
            fontSize = 22.sp,
            lineHeight = 28.0.sp,
            letterSpacing = 0.0.sp
        )

        Text(
            text = listDetailItem.description,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.2.sp
        )
    }
}