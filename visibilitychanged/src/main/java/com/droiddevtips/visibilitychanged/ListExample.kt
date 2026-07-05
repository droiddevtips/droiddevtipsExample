package com.droiddevtips.visibilitychanged

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onVisibilityChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ListExample(modifier: Modifier = Modifier, onTabVisible: (String) -> Unit) {

    Box(
        modifier = modifier
            .onVisibilityChanged { visible ->
                if (visible) {
                    onTabVisible(TabRoute.List.title)
                }
            }
    ) {

        val dummyList = (0..100).map { "Item $it" }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(dummyList) { item ->

                Row {
                    val isVisible = remember { mutableStateOf(false) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onVisibilityChanged(
                                minFractionVisible = 0.5f,
                                minDurationMs = 200
                            ) { visible ->
                                isVisible.value = visible
                            }
                            .then(
                                if (isVisible.value) {
                                    Modifier.background(color = Color.Blue)
                                } else {
                                    Modifier.background(color = Color.Red)
                                }
                            )
                    ) {
                        Text(
                            text = "$item ${if (isVisible.value) stringResource(id = R.string.item_visible) else stringResource(id = R.string.item_not_visible)}",
                            modifier = Modifier.padding(all = 16.dp)
                        )
                    }
                }
            }
        }
    }
}