@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.droiddevtips.masteringcomposetheme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droiddevtips.masteringcomposetheme.feature.button.ui.Material3ButtonExample
import com.droiddevtips.masteringcomposetheme.feature.checkbox.ui.CheckBoxExample
import com.droiddevtips.masteringcomposetheme.feature.datepicker.ui.Material3DatePicker
import com.droiddevtips.masteringcomposetheme.feature.progress.ui.Material3Progress
import com.droiddevtips.masteringcomposetheme.feature.textfield.ui.TextFieldExampleContainer
import com.droiddevtips.typography.DroidDevTipsTheme

class MasteringComposeThemeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MasteringThemeMainContainer(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun MasteringThemeMainContainer(modifier: Modifier = Modifier) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Material3ButtonExample()

        TextFieldExampleContainer()

        CheckBoxExample()

        Material3DatePicker()

        Material3Progress()

        Spacer(modifier = Modifier.height(32.dp))
    }
}