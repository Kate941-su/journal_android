package com.kaitokitaya.jounal

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kaitokitaya.jounal.repository.JournalRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
@Config(application = HiltTestApplication::class)
@HiltAndroidTest
class JournalUnitTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject lateinit var journalRepository: JournalRepository

    @Before
    fun setup() {
        hiltRule.inject()
    }
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}