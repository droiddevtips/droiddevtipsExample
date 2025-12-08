@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.droiddevtips.floatingtabbarandpip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.ui.Modifier
import com.droiddevtips.floatingtabbarandpip.common.pipManager.PipManager
import com.droiddevtips.floatingtabbarandpip.feature.main.MainView
import com.droiddevtips.typography.DroidDevTipsTheme

/**
 * The floating tab bar and pip example
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class FloatingTabBarAndPipMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DroidDevTipsTheme {
                BackHandler(true) {
                    if (PipManager.isInPipMode) {
                        moveTaskToBack(true)
                    } else {
                        finish()
                    }
                }
                MainView(modifier = Modifier.fillMaxSize())
            }
        }
    }
}