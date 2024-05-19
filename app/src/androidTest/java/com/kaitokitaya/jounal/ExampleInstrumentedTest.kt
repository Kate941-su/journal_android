package com.kaitokitaya.jounal

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kaitokitaya.jounal.mocking.MockedJournalRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.runner.RunWith
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */



@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject lateinit var journalRepository: MockedJournalRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }
//
//    @Test
//    fun enterFormulaShowsFormula() {
//        composeTestRule.setContent {
////            MainScreen(viewModel = MainScreenViewModel(journalRepository)) {
////
////            }
//        }
//    }
//
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.kaitokitaya.jounal", appContext.packageName)
    }
}