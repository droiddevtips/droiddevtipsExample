package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ViewType
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.util.Device
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.util.DeviceOrientation
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.util.currentWindowSize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Composable
fun DetailView(listDetailItem: ListDetailItem?, modifier: Modifier = Modifier) {

    if (listDetailItem == null)
        return

    val currentWindowSize = currentWindowSize()
    val density = LocalDensity.current
    val scrollable = rememberScrollState()
    val imageBoxActualHeight = rememberSaveable { mutableIntStateOf(200) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 30.dp)
            .verticalScroll(scrollable),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {

        if (listDetailItem.viewType == ViewType.Follower) {
            Image(
                painter = painterResource(id = listDetailItem.image),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp).clip(CircleShape),
                contentDescription = null
            )
        } else {
            Image(
                painter = painterResource(id = listDetailItem.image),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .background(color = Color(0xFFE6E6E6), shape = RoundedCornerShape(16.dp))
                    .then(
                        if (currentWindowSize.device == Device.Tablet && currentWindowSize.orientation == DeviceOrientation.Landscape) {
                            Modifier.fillMaxWidth(fraction = 0.6f)
                        } else {
                            Modifier.fillMaxWidth(fraction = 0.4f)
                        }
                    )
                    .height(imageBoxActualHeight.intValue.dp)
                    .onSizeChanged({ size ->

                        val widthInDp = with(density) {
                            size.width.toDp()
                        }

                        val actualImageHeight = (widthInDp.value / 100) * 78
                        imageBoxActualHeight.intValue = actualImageHeight.toInt()
                    }),
                contentDescription = null
            )
        }

        Text(
            listDetailItem.title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(listDetailItem.description)
    }

}