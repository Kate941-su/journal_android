package com.kaitokitaya.jounal.ui.theme.screens.edit_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaitokitaya.jounal.data.model.Journal
import com.kaitokitaya.jounal.type_define.VoidCallback
import com.kaitokitaya.jounal.ui.theme.screens.edit_screen.view_model.EditScreenViewModel
import com.kaitokitaya.jounal.ui.theme.screens.edit_screen.view_model.view_model_data.EmotionEnum
import com.kaitokitaya.jounal.ui.theme.screens.main_screen.view_model.PreviewJournalRepository
import com.kaitokitaya.jounal.ui.theme.screens.shared_components.JournalAppDropDownMenu
import com.kaitokitaya.jounal.ui.theme.screens.shared_components.NoRippleTextButton
import com.kaitokitaya.jounal.ui.theme.theme.tertiaryLight
import com.kaitokitaya.jounal.ui.theme.util.Util

private const val TAG = "Edit Screen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(journalId: Int, viewModel: EditScreenViewModel, onBackMainScreen: VoidCallback) {

    val localDate = Util.getLocalDateFromId(journalId)
    val title = viewModel.title.collectAsState()
    val content = viewModel.content.collectAsState()
    val emotion = viewModel.emotion.collectAsState()

    var isExpanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.initializeEditScreen(journalId)
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.setTitle("")
            viewModel.setContent("")
        }
    }

    val saveJournalAndBackMainScreen: VoidCallback = {
        if (title.value.isNotEmpty() || content.value.isNotEmpty()) {
            viewModel.onSave(
                journal = Journal(
                    id = journalId,
                    date = localDate,
                    title = title.value,
                    content = content.value,
                    emotion = "",
                )
            ) {
                // Completion handler
                onBackMainScreen()
            }
        } else {
            onBackMainScreen()
        }
    }

    // End Initialize
    EditContents(
        journalId = journalId,
        onBackMainScreen = saveJournalAndBackMainScreen,
        title = title.value,
        onTitleChanged = {
            viewModel.setTitle(it)
        },
        emotion = emotion.value,
        onEmotionChanged = {
            viewModel.setEmotion(it)
        },
        content = content.value,
        onContentChanged = {
            viewModel.setContent(it)
        },
        onTapCascadeMenu = { isExpanded = true },
        onDismissRequest = { isExpanded = false },
        isExpanded = isExpanded,
        onClickSave = saveJournalAndBackMainScreen,
        onClickDelete = {
            viewModel.deleteJournalById(journalId) {
                onBackMainScreen()
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContents(
    journalId: Int,
    onBackMainScreen: VoidCallback,
    title: String,
    onTitleChanged: (String) -> Unit,
    emotion: EmotionEnum,
    onEmotionChanged: (EmotionEnum) -> Unit,
    content: String,
    onContentChanged: (String) -> Unit,
    onTapCascadeMenu: (Boolean) -> Unit,
    onDismissRequest: (Boolean) -> Unit,
    isExpanded: Boolean,
    onClickSave: VoidCallback,
    onClickDelete: VoidCallback,
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
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = { onTapCascadeMenu(true) }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "menu")
                    }
                },
            )
        },
    ) { innerPadding ->
        JournalAppDropDownMenu(
            isExpanded = isExpanded,
            onDismissRequest = { onDismissRequest(false) },
            onSave = onClickSave,
            onDelete = onClickDelete,
        )
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
            Column(modifier = Modifier.padding(12.dp)) {
                Text("How do you feel now?", style = TextStyle(fontWeight = FontWeight.Bold))
                Row {
                    EmotionEnum.entries.forEach {
                        Box(contentAlignment = Alignment.Center) {
                            if (emotion == it) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(tertiaryLight)
                                )
                            }
//                            TextButton(onClick = { onEmotionChanged(it) },) {
//                                Text(it.emotion)
//                            }
                            NoRippleTextButton<EmotionEnum>(
                                onClick = { onEmotionChanged(it!!) },
                                something = it,
                                text = it.emotion
                            )
                        }
                    }
                }
            }
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
    EditScreen(journalId = 20000101, viewModel = EditScreenViewModel(repository = PreviewJournalRepository())) {
    }
}

