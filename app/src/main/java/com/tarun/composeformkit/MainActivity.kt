package com.tarun.composeformkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tarun.composeformkit.ui.screens.LeadCaptureScreen
import com.tarun.composeformkit.ui.theme.ComposeFormKitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeFormKitTheme {
                LeadCaptureScreen()
            }
        }
    }
}