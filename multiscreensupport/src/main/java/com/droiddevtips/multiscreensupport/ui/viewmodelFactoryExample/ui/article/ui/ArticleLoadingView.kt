package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.article.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droiddevtips.typography.DroidDevTipsTheme

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

@Composable
fun ArticleLoadingView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically)
    ) {
        CircularProgressIndicator()
        Text("Loading article.....")
    }
}

@Preview
@Composable
fun TestLoadingView() {

    DroidDevTipsTheme {
        ArticleLoadingView()
    }
}