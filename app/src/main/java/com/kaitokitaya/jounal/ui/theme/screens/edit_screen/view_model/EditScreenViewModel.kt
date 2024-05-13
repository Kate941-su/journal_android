package com.kaitokitaya.jounal.ui.theme.screens.edit_screen.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaitokitaya.jounal.data.model.Journal
import com.kaitokitaya.jounal.data.model.JournalDatabase
import com.kaitokitaya.jounal.repository.JournalRepository
import com.kaitokitaya.jounal.repository.RoomJournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditScreenViewModel @Inject constructor(private val repository: JournalRepository) : ViewModel() {

    private val _allJournals = MutableStateFlow<List<Journal>>(emptyList())

    private val allJournals: StateFlow<List<Journal>> = _allJournals.asStateFlow()

    private val _title = MutableStateFlow<String>("")
    var title: StateFlow<String> = _title.asStateFlow()

    private val _content = MutableStateFlow<String>("")
    var content: StateFlow<String> = _content.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllJournalStream().collect {
                _allJournals.value = it
            }
        }
    }

    fun onSave(journal: Journal) {
        viewModelScope.launch {
            if (allJournals.value.map { it.id }.contains(journal.id)) {
                repository.updateJournal(journal)
            } else {
                repository.insertJournal(journal)
            }
        }
    }

    fun deleteJournalById(id: Int) {
        viewModelScope.launch {
            val journal = repository.getJournalStream(id)
            journal.collect { res ->
                res?.let {
                    repository.deleteJournal(it)
                }
            }
        }
    }

    fun onClickDelete(journal: Journal) {
        viewModelScope.launch {
            repository.deleteJournal(journal)
        }
    }


    fun setTitle(title: String) {
        _title.update {
            title
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
                Log.d(TAG, "Title: ${journal?.title.toString()}")
                Log.d(TAG, "Content: ${journal?.content.toString()}")
                journal?.let {
                    setTitle(it.title)
                    setContent(it.content)
                }
            }
        }
    }


    companion object {
        private val TAG = this::class.java.simpleName
    }


}