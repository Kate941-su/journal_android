package com.kaitokitaya.jounal.ui.theme.screens.main_screen.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaitokitaya.jounal.ui.theme.util.Util
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.Date

data class Counter(val count: Int = 0)

class MainScreenViewModel: ViewModel() {
    private val _count = MutableStateFlow(Counter())
    val count: StateFlow<Counter> = _count.asStateFlow()

    private val _monthlyDate = MutableStateFlow(LocalDate.now())
    val monthlyDate: StateFlow<LocalDate> = _monthlyDate.asStateFlow()

    private val _monthlyDays = MutableStateFlow(Util.getMonthlyJournalDateList(_monthlyDate.value))
    val monthlyDays: StateFlow<List<Int?>> = _monthlyDays.asStateFlow()

    fun backMonth() {
        val newDate = _monthlyDate.value.minusMonths(1L)
        val dummy = Util.getMonthlyJournalDateList(newDate)
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