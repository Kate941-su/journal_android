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

data class Counter(val count: Int = 0)

class MainScreenViewModel: ViewModel() {
    private val _count = MutableStateFlow(Counter())
    val count: StateFlow<Counter> = _count.asStateFlow()

    fun increase() {
        _count.update {
            it.copy(count = it.count + 1)
        }
    }

}