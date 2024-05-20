package com.kaitokitaya.jounal.ui.theme.screens.edit_screen.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaitokitaya.jounal.data.model.Journal
import com.kaitokitaya.jounal.data.model.JournalDatabase
import com.kaitokitaya.jounal.repository.JournalRepository
import com.kaitokitaya.jounal.repository.RoomJournalRepository
import com.kaitokitaya.jounal.type_define.VoidCallback
import com.kaitokitaya.jounal.ui.theme.screens.edit_screen.view_model.view_model_data.EmotionEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class EditScreenViewModel @Inject constructor(private val repository: JournalRepository) : ViewModel() {

    private val _title = MutableStateFlow<String>("")
    var title: StateFlow<String> = _title.asStateFlow()

    private val _emotion = MutableStateFlow(EmotionEnum.GOOD)
    var emotion: StateFlow<EmotionEnum> = _emotion.asStateFlow()

    private val _content = MutableStateFlow<String>("")
    var content: StateFlow<String> = _content.asStateFlow()


    fun onSave(journal: Journal, completion: VoidCallback? = null) {
        viewModelScope.launch {
            val result = repository.getJournalStream(journal.id).firstOrNull()
            Log.d(TAG, "Result of getJournalStream(): $result")
            if (result == null) {
                repository.insertJournal(journal)
            } else {
                // Update could not work unless pk match as journal.
                try {
                    val journalWithPk = journal.copy(primKey = result.primKey)
                    Log.d(TAG, "Add pk to journal: $journalWithPk")
                    repository.updateJournal(journalWithPk)
                } catch (e: Exception){
                    // No error happens.
                    Log.d(TAG, e.toString())
                }
            }
            val dummy = repository.getAllJournalStream().first()
            Log.d(TAG, "Result of getAllJournalStream(): $dummy")
            completion?.invoke()
        }
    }

    fun deleteJournalById(id: Int, completion: VoidCallback? = null) {
        viewModelScope.launch {
            val journal = repository.getJournalStream(id).firstOrNull()
            journal?.let {
                repository.deleteJournal(journal)
            }
            completion?.invoke()
        }
    }

    fun setTitle(title: String) {
        _title.update {
            title
        }
    }

    fun setEmotion(emotion: EmotionEnum) {
        _emotion.update {
            emotion
        }
    }

    fun setContent(content: String) {
        _content.update {
            content
        }
    }

    override fun onCleared() {
        Log.d(TAG, "Viewmodel disposed")
        super.onCleared()
    }

    fun initializeEditScreen(id: Int) {
        viewModelScope.launch {
            repository.getJournalStream(id).collect { journal ->
                Log.d(TAG, "ID: $id")
                Log.d(TAG, "Title in Journal: ${journal?.title.toString()}")
                Log.d(TAG, "Content in Journal: ${journal?.content.toString()}")
                journal?.let {
                    setTitle(it.title)
                    setContent(it.content)
                    setEmotion(EmotionEnum.fromString(it.emotion))
                }
                Log.d(TAG, "Title in Field: ${title.value}")
                Log.d(TAG, "Content in Field: ${content.value}")
            }
        }
    }


    companion object {
        private val TAG = EditScreenViewModel::class.java.simpleName
    }


}