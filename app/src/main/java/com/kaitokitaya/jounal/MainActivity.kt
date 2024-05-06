package com.kaitokitaya.jounal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kaitokitaya.jounal.ui.theme.screens.edit_screen.EditScreen
import com.kaitokitaya.jounal.ui.theme.theme.JounalTheme
import com.kaitokitaya.jounal.ui.theme.screens.main_screen.MainScreen
import dagger.hilt.android.AndroidEntryPoint

enum class AppPath(val path: String) {
    MAIN_SCREEN("main_screen"),
    EDIT_SCREEN("edit_screen")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            JounalTheme {
                NavHost(navController = navController, startDestination = "main_screen") {
                    composable(AppPath.MAIN_SCREEN.path) {
//                        You can get context by LocalContext ↓
//                        val context = LocalContext.current
                        MainScreen {
                            Log.d(TAG,"NavHost is going to go to ${AppPath.EDIT_SCREEN.path}")
                            navController.navigate("${AppPath.EDIT_SCREEN.path}/$it")
                        }
                    }
                    composable("${AppPath.EDIT_SCREEN.path}/{journalId}") { backStackEntry ->
                        val journalId = backStackEntry.arguments?.getString("journalId")?.toInt() ?: -1
                        Log.d(TAG, journalId.toString())
                        EditScreen(journalId = journalId) {
                            Log.d(TAG,"NavHost is going to go to ${AppPath.MAIN_SCREEN.path}")
                            navController.navigate(AppPath.MAIN_SCREEN.path)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private val TAG = this::class.java.simpleName
    }

}