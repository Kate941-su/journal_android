package com.kaitokitaya.jounal.ui.theme.screens.editScreen.uiData

import androidx.compose.ui.graphics.vector.ImageVector
import com.kaitokitaya.jounal.AppPath

data class NavigationItem(
    val appPath: AppPath,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
)
