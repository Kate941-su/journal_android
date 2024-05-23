package com.kaitokitaya.jounal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kaitokitaya.jounal.ui.theme.screens.editScreen.EditScreen
import com.kaitokitaya.jounal.ui.theme.screens.editScreen.viewModel.EditScreenViewModel
import com.kaitokitaya.jounal.ui.theme.screens.mainScreen.MainScreen
import com.kaitokitaya.jounal.ui.theme.screens.mainScreen.viewModel.MainScreenViewModel
import com.kaitokitaya.jounal.ui.theme.screens.settingsScreen.uiData.SettingsScreen
import com.kaitokitaya.jounal.ui.theme.screens.timeLineScreen.uiData.TimeLineScreen
import com.kaitokitaya.jounal.ui.theme.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

enum class AppPath(val path: String) {
    CALENDAR_SCREEN("main_screen"),
    EDIT_SCREEN("edit_screen"),
    TIME_LINE_SCREEN("time_line"),
    SETTINGS_SCREEN("settings_screen"),
    UNDEFINED(""),
    ;

    companion object {
        fun getAppPath(index: Int): AppPath {
            return when (index) {
                CALENDAR_SCREEN.ordinal -> CALENDAR_SCREEN
                EDIT_SCREEN.ordinal -> EDIT_SCREEN
                TIME_LINE_SCREEN.ordinal -> TIME_LINE_SCREEN
                SETTINGS_SCREEN.ordinal -> SETTINGS_SCREEN
                else -> UNDEFINED
            }
        }
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val mainScreenViewModel: MainScreenViewModel = viewModel()
            val editScreenViewModel: EditScreenViewModel = viewModel()
            AppTheme {
                NavHost(navController = navController, startDestination = AppPath.CALENDAR_SCREEN.path) {
                    composable(AppPath.CALENDAR_SCREEN.path) {
//                        You can get context by LocalContext ↓
//                        val context = LocalContext.current
                        MainScreen(mainScreenViewModel, onTapDate = {
                            Log.d(TAG, "NavHost is going to go to ${AppPath.EDIT_SCREEN.path}")
                            navController.navigate("${AppPath.EDIT_SCREEN.path}/$it")
                        }) {
                            navController.navigate(it.path)
                        }
                    }
                    composable("${AppPath.EDIT_SCREEN.path}/{journalId}") { backStackEntry ->
                        val journalId = backStackEntry.arguments?.getString("journalId")?.toInt() ?: -1
                        Log.d(TAG, journalId.toString())
                        EditScreen(journalId = journalId, viewModel = editScreenViewModel) {
                            Log.d(TAG, "NavHost is going to go to ${AppPath.CALENDAR_SCREEN.path}")
                            navController.navigate(AppPath.CALENDAR_SCREEN.path)
                        }
                    }
                    composable(AppPath.TIME_LINE_SCREEN.path) {
                        TimeLineScreen()
                    }
                    composable(AppPath.SETTINGS_SCREEN.path) {
                        SettingsScreen()
                    }
                }
            }
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
