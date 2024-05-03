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
import com.kaitokitaya.jounal.ui.theme.screens.main_screen.components.JournalPreviewCard
import com.kaitokitaya.jounal.ui.theme.screens.main_screen.view_model.MainScreenViewModel
import com.kaitokitaya.jounal.ui.theme.static_data.StaticData
import com.kaitokitaya.jounal.ui.theme.util.Util
import java.time.LocalDate
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaitokitaya.jounal.type_define.VoidCallback
import kotlin.math.ceil

private const val TAG = "MainScreen"
private const val WEEK_DAYS = 7

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = viewModel()
) {
    rememberTopAppBarState()
    val count by viewModel.count.collectAsState()
    val date by viewModel.monthlyDate.collectAsState()
    val monthlyDays by viewModel.monthlyDays.collectAsState()
    MainContent(
        count = count.count,
        date = date,
        monthlyDays = monthlyDays,
        increase = { viewModel.increase() },
        forwardDate = { viewModel.forwardMonth() },
        backDate = { viewModel.backMonth() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    count: Int,
    date: LocalDate,
    monthlyDays: List<Int?>,
    increase: VoidCallback,
    forwardDate: VoidCallback,
    backDate: VoidCallback
) {
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
                    Text("Calender $count")
                },
                modifier = Modifier.padding(0.dp),
                actions = {
                    Log.d(TAG, "TODO: Open/Close hamburger menu")
                },
            )
        },
        floatingActionButton = {
            IconButton(onClick = increase) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            MonthlyBar(date = date, forwardDate = forwardDate, backDate = backDate)
            WeeklyBar()
            MonthlyContents(monthlyDays = monthlyDays)
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
fun MonthlyBar(date: LocalDate, forwardDate: VoidCallback, backDate: VoidCallback) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = backDate) {
            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "previous")
        }
        Text(text = "${date.month}", fontSize = 24.sp)
        Text(
            text = "${date.year}", modifier = Modifier.padding(8.dp), fontSize = 24.sp
        )
        IconButton(onClick = forwardDate) {
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
fun MonthlyContents(monthlyDays: List<Int?>) {
    Column {
        val outerLoopNum = ceil(monthlyDays.size.toFloat() / WEEK_DAYS).toInt()
        var count = 0
        for (i in 0 until outerLoopNum) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                for (j in 0 until WEEK_DAYS) {
                    MonthlyContentItem(date = monthlyDays[count], modifier = Modifier.padding(all = 2.dp))
                    count++
                }
            }
        }
    }
}

@Composable
fun MonthlyContentItem(
    date: Int?,
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
            text = "${date ?: ""}",
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