package com.kaitokitaya.jounal.ui.theme.screens.edit_screen.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaitokitaya.jounal.data.model.Journal
import com.kaitokitaya.jounal.data.model.JournalDatabase
import com.kaitokitaya.jounal.repository.JournalRepository
import com.kaitokitaya.jounal.repository.RoomJournalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditScreenViewModel @Inject constructor(private val repository: JournalRepository) : ViewModel() {

    private val _allJournals = MutableStateFlow<List<Journal>>(emptyList())

    private val allJournals: StateFlow<List<Journal>> = _allJournals.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllJournalStream().collect {
                _allJournals.value = it
            }
        }
    }

    fun onBackMain(journal: Journal) {
        viewModelScope.launch {
            if (allJournals.value.map { it.id }.contains(journal.id)) {
                repository.updateJournal(journal)
            } else {
                repository.insertJournal(journal)
            }

        }
    }
}