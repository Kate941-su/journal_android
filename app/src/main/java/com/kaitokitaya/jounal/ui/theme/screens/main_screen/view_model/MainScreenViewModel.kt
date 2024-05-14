package com.kaitokitaya.jounal.ui.theme.screens.main_screen.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaitokitaya.jounal.data.model.Journal
import com.kaitokitaya.jounal.repository.JournalRepository
import com.kaitokitaya.jounal.ui.theme.util.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

data class Counter(val count: Int = 0)

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val journalRepository: JournalRepository) : ViewModel() {
    private val _count = MutableStateFlow(Counter())
    val count: StateFlow<Counter> = _count.asStateFlow()

    private val _monthlyDate = MutableStateFlow(LocalDate.now())
    val monthlyDate: StateFlow<LocalDate> = _monthlyDate.asStateFlow()

    private val _monthlyDays = MutableStateFlow(Util.getMonthlyJournalDateList(_monthlyDate.value))
    val monthlyDays: StateFlow<List<Int?>> = _monthlyDays.asStateFlow()

    private val _allJournals = MutableStateFlow<List<Journal>>(emptyList())
    val allJournals: StateFlow<List<Journal>> = _allJournals.asStateFlow()

    init {
        initializeMainScreen()
    }

    fun initializeMainScreen() {
        viewModelScope.launch {
            _allJournals.value = journalRepository.getAllJournalStream().first()
        }
    }

    fun backMonth() {
        val newDate = _monthlyDate.value.minusMonths(1L)
        _monthlyDate.update {
            newDate
        }
        _monthlyDays.update {
            Util.getMonthlyJournalDateList(newDate)
        }
    }

    fun forwardMonth() {
        val newDate = _monthlyDate.value.plusMonths(1L)
        _monthlyDate.update {
            newDate
        }
        _monthlyDays.update {
            Util.getMonthlyJournalDateList(newDate)
        }
    }

    fun increase() {
        _count.update {
            it.copy(count = it.count + 1)
        }
    }

}