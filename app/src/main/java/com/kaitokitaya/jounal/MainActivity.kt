package com.kaitokitaya.jounal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kaitokitaya.jounal.ui.theme.theme.JounalTheme
import com.kaitokitaya.jounal.ui.theme.screens.main_screen.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JounalTheme {
                MainScreen()
            }
        }
    }
}