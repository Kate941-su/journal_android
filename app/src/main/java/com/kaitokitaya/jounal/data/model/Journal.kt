package com.kaitokitaya.jounal.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDate

@Entity(tableName = "journal_table")
data class Journal(
    @PrimaryKey
    val primKey: Int,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "local_date")
    val date: LocalDate,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
)

class LocalDateConverter {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @TypeConverter
    fun fromLocalDate(localDate: LocalDate): String {
        return localDate.toString()
    }

    @TypeConverter
    fun toLocalDate(localDateString: String): LocalDate {
        return LocalDate.parse(localDateString)
    }
}