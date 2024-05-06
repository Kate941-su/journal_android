package com.kaitokitaya.jounal.di

import android.content.Context
import com.kaitokitaya.jounal.data.model.JournalDAO
import com.kaitokitaya.jounal.data.model.JournalDatabase
import com.kaitokitaya.jounal.repository.JournalRepository
import com.kaitokitaya.jounal.repository.RoomJournalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JournalModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): JournalDatabase = JournalDatabase.getDatabase(context = context)

    @Provides
    fun provideJournalDao(database: JournalDatabase): JournalDAO = database.journalDao()

    @Provides
    fun provideJournalRepository(journalDAO: JournalDAO): JournalRepository = RoomJournalRepository(journalDAO)


}