package com.kaitokitaya.jounal.ui.theme.screens.editScreen.viewModel.viewModelData

import android.util.Log

enum class EmotionEnum(val emotion: String) {
    GOOD("☀️"),
    CLOUDY("☁️"),
    BAD("☔️"),
    RAGE("⚡️"),
    CONGRATS("️🎉"),
    LOVE("❤️"),
    ;

    companion object {
        fun fromString(emotionString: String): EmotionEnum {
            return when (emotionString) {
                "☀️" -> GOOD
                "☁️" -> CLOUDY
                "☂️" -> BAD
                "⚡️" -> RAGE
                "🎉" -> CONGRATS
                "❤️" -> LOVE
                else -> {
                    // Valid from canary test
//                    assert(false) {"Get unexpected character from DB"}
                    Log.d(TAG, "Get unexpected character from DB")
                    return GOOD
                }
            }
        }

        private val TAG = EmotionEnum::class.java.simpleName
    }
}
