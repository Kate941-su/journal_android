package com.kaitokitaya.jounal.ui.theme.screens.main_screen.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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

    fun backMonth() {
        val newDate = _monthlyDate.value.minusMonths(1L)
        _monthlyDate.update {
            newDate
        }
    }

    fun forwardMonth() {
        val newDate = _monthlyDate.value.plusMonths(1L)
        _monthlyDate.update {
            newDate
        }
    }

    fun increase() {
        _count.update {
            it.copy(count = it.count + 1)
        }
    }

}