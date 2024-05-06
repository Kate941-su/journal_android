package com.kaitokitaya.jounal.repository

import com.kaitokitaya.jounal.data.model.Journal
import kotlinx.coroutines.flow.Flow

interface JournalRepository {
    suspend fun insertJournal(journal: Journal)
    suspend fun updateJournal(journal: Journal)

    suspend fun deleteJournal(journal: Journal)

    fun getJournalStream(id: Int): Flow<Journal?>

    fun getAllJournalStream(): Flow<List<Journal>>

}