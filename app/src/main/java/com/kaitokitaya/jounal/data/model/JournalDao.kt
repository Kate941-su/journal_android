package com.kaitokitaya.jounal.data.model

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDAO {

    @Insert
    suspend fun insertJournal(journal: Journal)

    @Update
    suspend fun updateJournal(journal: Journal)

    @Delete
    suspend fun deleteNote(journal: Journal)

    @Query("SELECT * FROM journal_table WHERE id= :id")
    fun getJournal(id: Int): Flow<Journal>

    @Query("SELECT * FROM journal_table")
    fun getAllJournals(): Flow<List<Journal>>
}

@TypeConverters(LocalDateConverter::class)
@Database(
    entities = [Journal::class],
    version = 1
)
abstract class JournalDatabase : RoomDatabase() {

    abstract fun journalDao(): JournalDAO

    companion object {
        @Volatile
        private var Instance: JournalDatabase? = null

        fun getDatabase(context: Context): JournalDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, JournalDatabase::class.java, "journal_database").build()
                    .also { Instance = it }
            }
        }
    }


}