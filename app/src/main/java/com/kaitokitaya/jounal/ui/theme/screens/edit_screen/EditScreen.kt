package com.kaitokitaya.jounal.ui.theme.screens.edit_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaitokitaya.jounal.type_define.VoidCallback
import com.kaitokitaya.jounal.ui.theme.screens.edit_screen.view_model.EditScreenViewModel
import com.kaitokitaya.jounal.ui.theme.screens.main_screen.MainScreen
import com.kaitokitaya.jounal.ui.theme.theme.AppColor
import com.kaitokitaya.jounal.ui.theme.util.Util

private const val TAG = "Edit Screen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(journalId: Int, viewModel: EditScreenViewModel = viewModel(), onBackMainScreen: VoidCallback) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    EditContents(
        journalId = journalId,
        onBackMainScreen = onBackMainScreen,
        title = title,
        onTitleChanged = { title = it },
        content = content,
        onContentChanged = { content = it }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContents(
    journalId: Int,
    onBackMainScreen: VoidCallback,
    title: String,
    onTitleChanged: (String) -> Unit,
    content: String,
    onContentChanged: (String) -> Unit
) {
    val localDate = Util.getLocalDateFromId(journalId)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackMainScreen) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                title = {
                    Text(
                        text = "${localDate.dayOfWeek}," +
                                "  ${localDate.month}," +
                                " ${localDate.dayOfMonth}," +
                                " ${localDate.year}"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColor.appBarColorLightTheme,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = { Log.d(TAG, "Open Menu") }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "menu")
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top
        ) {
            TextField(
                value = title,
                singleLine = true,
                textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                label = { Text("Title", style = TextStyle(fontWeight = FontWeight.Bold)) },
                onValueChange = onTitleChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )
            TextField(
                value = content,
                textStyle = TextStyle(fontSize = 18.sp),
                label = { Text("Text") },
                onValueChange = onContentChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(12.dp)
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun EditScreenPreview() {
    EditScreen(journalId = 20000101) {

    }
}

