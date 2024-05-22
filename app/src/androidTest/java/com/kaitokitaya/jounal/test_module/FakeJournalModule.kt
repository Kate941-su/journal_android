// One of the mocking way to mock hilt.
package com.kaitokitaya.jounal.test_module

import android.content.Context
import androidx.room.Room
import com.kaitokitaya.jounal.data.model.JournalDAO
import com.kaitokitaya.jounal.data.model.JournalDatabase
import com.kaitokitaya.jounal.di.JournalModule
import com.kaitokitaya.jounal.mocking.MockedJournalRepository
import com.kaitokitaya.jounal.repository.JournalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    // Define the module you want to mock.
    replaces = [JournalModule::class],
)
object FakeJournalModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): JournalDatabase {
        return Room.inMemoryDatabaseBuilder(context, JournalDatabase::class.java)
            .allowMainThreadQueries().build()
    }

    @Provides
    fun provideJournalDao(database: JournalDatabase): JournalDAO {
        return mockk<JournalDAO>(relaxed = true)
    }

    @Provides
    fun provideJournalRepository(journalDAO: JournalDAO): JournalRepository = MockedJournalRepository()
}
