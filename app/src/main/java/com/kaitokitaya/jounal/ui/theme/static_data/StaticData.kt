package com.kaitokitaya.jounal.ui.theme.static_data

object StaticData {
    val weekDays: List<String> = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sut")
}

enum class WeekDays(name: String) {
    SUN("Sun"),
    MON("Mon"),
    TUE("Tue"),
    WED("Wed"),
    THU("Thu"),
    FRI("Fri"),
    STU("Sat");
}