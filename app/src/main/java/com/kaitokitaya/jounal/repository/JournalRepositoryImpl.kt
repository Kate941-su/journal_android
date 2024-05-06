package com.kaitokitaya.jounal.repository

import com.kaitokitaya.jounal.data.model.Journal
import com.kaitokitaya.jounal.data.model.JournalDAO
import kotlinx.coroutines.flow.Flow

class RoomJournalRepository(private val journalDAO: JournalDAO) : JournalRepository {
    override suspend fun insertJournal(journal: Journal) = journalDAO.insertJournal(journal)

    override suspend fun updateJournal(journal: Journal) = journalDAO.updateJournal(journal)

    override suspend fun deleteJournal(journal: Journal) = journalDAO.deleteNote(journal)

    override fun getJournalStream(id: Int): Flow<Journal?> = journalDAO.getJournal(id)

    override fun getAllJournalStream(): Flow<List<Journal>> = journalDAO.getAllJournals()

}