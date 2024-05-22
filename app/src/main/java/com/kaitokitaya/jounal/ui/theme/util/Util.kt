package com.kaitokitaya.jounal.ui.theme.util

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import java.time.DayOfWeek
import java.time.LocalDate

object Util {
    private val yearToIdTerm = 10000
    private val monthToIdTerm = 100

    @Composable
    fun getPlatformConfiguration(): Configuration {
        return LocalConfiguration.current
    }

    fun getMonthlyJournalDateList(date: LocalDate): List<Int?> {
        val monthlyDays = mutableListOf<Int?>()
        val firstDay = LocalDate.of(date.year, date.month, 1)
        val lengthOfMonth = firstDay.lengthOfMonth()
        val lastDay = LocalDate.of(date.year, date.month, lengthOfMonth)
        val firstDayOfWeek = fixSundayIndexToZero(firstDay.dayOfWeek.value)
        val lastDayOfWeek = fixSundayIndexToZero(lastDay.dayOfWeek.value)
        repeat(firstDayOfWeek) {
            monthlyDays.add(null)
        }
        for (i in 1..lengthOfMonth) {
            monthlyDays.add(i)
        }
        repeat(DayOfWeek.SATURDAY.value - lastDayOfWeek) {
            monthlyDays.add(null)
        }
        return monthlyDays
    }

    private fun fixSundayIndexToZero(weekIndex: Int): Int {
        return when (weekIndex) {
            DayOfWeek.SUNDAY.value -> 0
            else -> weekIndex
        }
    }

    fun createJournalIdFromLocalDate(
        year: Int,
        month: Int,
        day: Int,
    ): Int {
        return year * 10000 + month * 100 + day
    }

    fun getLocalDateFromId(id: Int): LocalDate {
        val year = (id / yearToIdTerm)
        val month = (id - year * yearToIdTerm) / monthToIdTerm
        val day = (id - year * yearToIdTerm - month * monthToIdTerm)
        return LocalDate.of(year, month, day)
    }
}
