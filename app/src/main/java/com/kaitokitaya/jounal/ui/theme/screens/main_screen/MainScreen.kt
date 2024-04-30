package com.kaitokitaya.jounal.ui.theme.screens.main_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.kaitokitaya.jounal.data.model.JournalDate
import com.kaitokitaya.jounal.ui.theme.screens.main_screen.components.JournalPreviewCard
import com.kaitokitaya.jounal.ui.theme.screens.main_screen.view_model.MainScreenViewModel
import com.kaitokitaya.jounal.ui.theme.static_data.StaticData
import com.kaitokitaya.jounal.ui.theme.util.Util
import java.time.LocalDate
import androidx.lifecycle.viewmodel.compose.viewModel

private const val TAG = "MainScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = viewModel()
) {
    rememberTopAppBarState()
    val count by viewModel.count.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = ""
                        )
                    }
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Calender ${count.count}")
                },
                modifier = Modifier.padding(0.dp),
                actions = {
                    Log.d(TAG, "TODO: Open/Close hamburger menu")
                },
            )
        },
        floatingActionButton = {
            IconButton(onClick = {
                viewModel.increase()
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            MonthlyBar()
            WeeklyBar()
            MonthlyContents()
            Divider()
            Box(
                modifier = Modifier
                    .background(color = Color.Red)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {

            }
            }
        }
    }

@Composable
fun MonthlyBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { Log.d(TAG, "slide left") }) {
            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "previous")
        }
        Text(text = "May", fontSize = 24.sp)
        Text(
            text = "2024", modifier = Modifier.padding(8.dp), fontSize = 24.sp
        )
        IconButton(onClick = { Log.d(TAG, "slide right") }) {
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = "next")
        }
    }
}

@Composable
fun WeeklyBar() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        StaticData.weekDays.forEach {
            DayItem(day = it, modifier = Modifier.padding(2.dp))
        }
    }
}

@Composable
fun DayItem(day: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(color = Color.Red)
            .size(Util.getPlatformConfiguration().screenWidthDp.dp / 8, 24.dp)
    ) {
        Text(
            textAlign = TextAlign.Center,
            softWrap = false,
            text = day,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun MonthlyContents() {
    Column {
        /// TODO: The following implementation is dummy
        repeat(6) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                repeat(7) {
                    MonthlyContentItem(date = JournalDate(date = null), modifier = Modifier.padding(all = 2.dp))
                }
            }
        }
    }
}

@Composable
fun MonthlyContentItem(
    date: JournalDate,
    modifier: Modifier = Modifier
) {
    val configuration = Util.getPlatformConfiguration()
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                color = Color.Green
            )
            .clickable {
                Log.d(TAG, "date tapped")
            }
            .size(Util.getPlatformConfiguration().screenWidthDp.dp / 8)
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "${date.date?.dayOfMonth ?: "1"}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}