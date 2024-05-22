package com.kaitokitaya.jounal.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDate

@Entity(tableName = "journal_table")
data class Journal(
    @PrimaryKey(autoGenerate = true)
    val primKey: Int? = null,
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "local_date")
    val date: LocalDate,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "emotion", defaultValue = "")
    val emotion: String,
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
