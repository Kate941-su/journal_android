package com.kaitokitaya.jounal.ui.theme.screens.mainScreen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaitokitaya.jounal.data.model.Journal
import com.kaitokitaya.jounal.type_define.VoidCallback
import com.kaitokitaya.jounal.ui.theme.screens.editScreen.uiData.NavigationItem
import com.kaitokitaya.jounal.ui.theme.screens.mainScreen.viewModel.MainScreenViewModel
import com.kaitokitaya.jounal.ui.theme.screens.mainScreen.viewModel.PreviewJournalRepository
import com.kaitokitaya.jounal.ui.theme.staticData.WeekDays
import com.kaitokitaya.jounal.ui.theme.theme.outlineDark
import com.kaitokitaya.jounal.ui.theme.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.ceil

private const val TAG = "MainScreen"
private const val WEEK_DAYS = 7
private const val PAGE_COUNT = 10000
private const val INITIAL_PAGE = PAGE_COUNT / 2

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    onTapDate: (Int) -> Unit,
) {
    val date by viewModel.monthlyDate.collectAsState()
    val monthlyDays by viewModel.monthlyDays.collectAsState()
    val allJournals by viewModel.allJournals.collectAsState()

    rememberTopAppBarState()
    // remember states
    var currentPage by rememberSaveable {
        mutableIntStateOf(INITIAL_PAGE)
    }
    val pagerState = rememberPagerState(pageCount = { PAGE_COUNT }, initialPage = INITIAL_PAGE)
    var today by rememberSaveable {
        mutableStateOf(LocalDate.now())
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val onClickNavigationMenu: (Int) -> Unit = { selectedItemIndex = it }

    LaunchedEffect(Unit) {
        viewModel.initializeMainScreen()
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (currentPage < page) {
                viewModel.forwardMonth()
            } else if (currentPage > page) {
                viewModel.backMonth()
            }
            currentPage = page
        }
    }

    MainContent(
        date = date,
        today = today,
        monthlyDays = monthlyDays,
        allJournals = allJournals,
        increase = { viewModel.increase() },
        forwardDate = { viewModel.forwardMonth() },
        backDate = { viewModel.backMonth() },
        onTapDate = onTapDate,
        pagerState = pagerState,
        drawerState = drawerState,
        scope = scope,
        selectedItemIndex = selectedItemIndex,
        onClickNavigationMenu = onClickNavigationMenu,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainContent(
    date: LocalDate,
    today: LocalDate,
    monthlyDays: List<Int?>,
    allJournals: List<Journal>,
    increase: VoidCallback,
    forwardDate: VoidCallback,
    backDate: VoidCallback,
    onTapDate: (Int) -> Unit,
    pagerState: PagerState,
    drawerState: DrawerState,
    scope: CoroutineScope,
    selectedItemIndex: Int,
    onClickNavigationMenu: (Int) -> Unit,
) {
    val items =
        listOf(
            NavigationItem(
                title = "Calendar",
                selectedIcon = Icons.Filled.DateRange,
                unselectedIcon = Icons.Outlined.DateRange,
            ),
            NavigationItem(
                title = "Timeline",
                selectedIcon = Icons.Filled.Star,
                unselectedIcon = Icons.Outlined.Star,
            ),
            NavigationItem(
                title = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
            ),
            NavigationItem(
                title = "Information",
                selectedIcon = Icons.Filled.Info,
                unselectedIcon = Icons.Outlined.Info,
            ),
        )

    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {
            items.forEachIndexed { index, item ->
                NavigationDrawerItem(
                    label = { Text(item.title) },
                    selected = index == selectedItemIndex,
                    onClick = {
                        onClickNavigationMenu(index)
                        scope.launch {
                            drawerState.close()
                        }
                    },
                )
            }
        }
    }, drawerState = drawerState) {
        Scaffold(
            topBar = {
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
                        topAppBarColors(
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                    title = {
                        Text("Calender")
                    },
                    modifier = Modifier.padding(0.dp),
                    actions = {
                        Log.d(TAG, "TODO: Open/Close hamburger menu")
                    },
                )
            },
        ) { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(0.dp),
            ) {
                MonthlyBar(date = date, forwardDate = forwardDate, backDate = backDate)
                WeeklyBar()
                MonthlyContents(
                    today = today,
                    month = date,
                    monthlyDays = monthlyDays,
                    allJournals = allJournals,
                    onTapDate = onTapDate,
                    pagerState = pagerState,
                )
                HorizontalDivider()
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                ) {
                }
            }
        }
    }
}

@Composable
fun MonthlyBar(
    date: LocalDate,
    forwardDate: VoidCallback,
    backDate: VoidCallback,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
    ) {
        IconButton(onClick = backDate) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "previous")
        }
        Text(text = "${date.month}", fontSize = 24.sp)
        Text(
            text = "${date.year}",
            modifier = Modifier.padding(8.dp),
            fontSize = 24.sp,
        )
        IconButton(onClick = forwardDate) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "next")
        }
    }
}

@Composable
fun WeeklyBar() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        WeekDays.values().forEach {
            DayItem(day = it, modifier = Modifier.padding(2.dp))
        }
    }
}

@Composable
fun DayItem(
    day: WeekDays,
    modifier: Modifier = Modifier,
) {
    var labelColor: Color = outlineDark
    if (day == WeekDays.SUN) {
        labelColor = Color.Red
    } else if (day == WeekDays.STU) {
        labelColor = Color.Blue
    }
    Box(
        modifier =
            modifier
                .background(color = labelColor)
                .size(Util.getPlatformConfiguration().screenWidthDp.dp / 8, 24.dp),
    ) {
        Text(
            textAlign = TextAlign.Center,
            softWrap = false,
            text = day.name,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier =
                Modifier
                    .fillMaxWidth(),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MonthlyContents(
    today: LocalDate,
    month: LocalDate,
    monthlyDays: List<Int?>,
    allJournals: List<Journal>,
    pagerState: PagerState,
    onTapDate: (Int) -> Unit,
) {
    HorizontalPager(
        state = pagerState,
    ) { page ->
        Column {
            val outerLoopNum = ceil(monthlyDays.size.toFloat() / WEEK_DAYS).toInt()
            var count = 0
            for (i in 0 until outerLoopNum) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    for (j in 0 until WEEK_DAYS) {
                        MonthlyContentItem(
                            day = monthlyDays[count],
                            today = today,
                            localDate = month,
                            modifier = Modifier.padding(all = 2.dp),
                            allJournals = allJournals,
                            onTapDate = onTapDate,
                        )
                        count++
                    }
                }
            }
        }
    }
}

@Composable
fun MonthlyContentItem(
    day: Int?,
    today: LocalDate,
    localDate: LocalDate,
    modifier: Modifier = Modifier,
    allJournals: List<Journal>,
    onTapDate: (Int) -> Unit,
) {
    val configuration = Util.getPlatformConfiguration()
    val allJournalIds = allJournals.map { it.id }
    val id =
        if (day != null) {
            Util.createJournalIdFromLocalDate(
                year = localDate.year,
                month = localDate.month.value,
                day = day,
            )
        } else {
            -1
        }

    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .background(
                    color = if (allJournalIds.contains(id)) Color.Blue else Color.Green.copy(alpha = if (day == null) 0.5F else 1F),
                )
                .clickable {
                    day?.let {
                        onTapDate(id)
                    }
                }
                .size(Util.getPlatformConfiguration().screenWidthDp.dp / 8),
    ) {
        if (localDate.year == today.year && localDate.month == today.month && day == today.dayOfMonth) {
            Box(
                modifier =
                    Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .border(width = 1.dp, color = Color.Black, shape = CircleShape),
            )
        }
        Text(
            textAlign = TextAlign.Center,
            text = "${day ?: ""}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen(viewModel = MainScreenViewModel(journalRepository = PreviewJournalRepository())) {
    }
}
