package com.kaitokitaya.jounal.ui.theme.screens.main_screen.view_model

import com.kaitokitaya.jounal.data.model.Journal
import com.kaitokitaya.jounal.repository.JournalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

class PreviewJournalRepository: JournalRepository {
    override suspend fun insertJournal(journal: Journal) {}

    override suspend fun updateJournal(journal: Journal) {}

    override suspend fun deleteJournal(journal: Journal) {}

    override fun getJournalStream(id: Int): Flow<Journal?> {
        return flowOf(null)
    }

    override fun getAllJournalStream(): Flow<List<Journal>> {
        val mockJournals = listOf(
            Journal(
                id = 0,
                date = LocalDate.now(),
                title = "DUMMY",
                content = "DUMMY CONTENT"
            )
        )
        return flowOf(mockJournals)
    }
}