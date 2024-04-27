package com.kaitokitaya.jounal.ui.theme.util

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.kaitokitaya.jounal.data.model.JournalDate
import java.time.LocalDate
import java.util.Date

object Util {



    @Composable
    fun getPlatformConfiguration(): Configuration {
        return LocalConfiguration.current
    }

    fun getMonthlyJournalDateList(date: LocalDate): List<JournalDate> {
        val firstDay = LocalDate.of(date.year, date.month, 1)
        val lengthOfMonth = firstDay.lengthOfMonth()
        val lastDay = LocalDate.of(date.year, date.month, lengthOfMonth)
        val firstDayOfWeek = firstDay.dayOfWeek
        val lastDayOfWeek = lastDay.dayOfWeek
        return emptyList()
    }
}