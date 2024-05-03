package com.kaitokitaya.jounal.data.model

import java.time.DayOfWeek

enum class WeeklyNumber(private val week: String) {
    SUNDAY("SUNDAY"),
    MONDAY("MONDAY"),
    TUESDAY("TUESDAY"),
    WEDNESDAY("WEDNESDAY"),
    THURSDAY("THURSDAY"),
    FRIDAY("FRIDAY"),
    SATURDAY("SATURDAY");

    companion object {
        fun getNumber(weekString: String): Int {
            return when (weekString) {
                SUNDAY.week -> SUNDAY.ordinal
                MONDAY.week -> MONDAY.ordinal
                TUESDAY.week -> TUESDAY.ordinal
                WEDNESDAY.week -> WEDNESDAY.ordinal
                THURSDAY.week -> THURSDAY.ordinal
                FRIDAY.week -> FRIDAY.ordinal
                SATURDAY.week -> SATURDAY.ordinal
                else -> -1
            }
        }
    }
}