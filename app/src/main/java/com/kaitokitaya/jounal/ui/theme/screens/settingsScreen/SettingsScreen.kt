package com.kaitokitaya.jounal.ui.theme.screens.settingsScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.kaitokitaya.jounal.R
import com.kaitokitaya.jounal.ui.theme.screens.settingsScreen.uiData.TopBarNavigationItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAG = "SettingScreen"

private val settingItems =
    listOf(
        TopBarNavigationItems(
            title = "General",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        ),
        TopBarNavigationItems(
            title = "Information",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        ),
        TopBarNavigationItems(
            title = "Recommendations",
            selectedIcon = Icons.Filled.LocationOn,
            unselectedIcon = Icons.Outlined.LocationOn,
        ),
    )

@Composable
fun SettingsScreen() {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val selectedItem by remember {
        mutableIntStateOf(0)
    }
    ScreenContents(
        scope = scope,
        drawerState = drawerState,
        selectedItem = selectedItem,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContents(
    scope: CoroutineScope,
    drawerState: DrawerState,
    selectedItem: Int,
) {
    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "",
                            )
                        }
                    },
                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                    title = {
                        Text("Settings")
                    },
                    actions = {
                        Log.d(TAG, "TODO: Open/Close hamburger menu")
                    },
                )
            }
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SettingCard(
                title = "App Theme",
                selectedItemDescription = "DUMMY",
                icon = ImageVector.vectorResource(id = R.drawable.ic_contrast),
                contentDescription = "contrast",
            )
        }
    }
}

@Composable
fun SettingCard(
    title: String,
    selectedItemDescription: String,
    icon: ImageVector,
    contentDescription: String,
) {
    Row {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
        )
        Column {
            Text(text = title, style = TextStyle(fontWeight = FontWeight.Bold))
            Text(text = selectedItemDescription)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    SettingsScreen()
}
