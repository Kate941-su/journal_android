package com.kaitokitaya.jounal.ui.theme.screens.shared_components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun <T> NoRippleTextButton(
    onClick: (T?) -> Unit,
    something: T? = null,
    text: String,
) {
    val interactionSource = remember { MutableInteractionSource() }

    TextButton(
        onClick = { onClick(something) },
        interactionSource = interactionSource,
        colors =
            ButtonDefaults.textButtonColors(
                contentColor = Color.Black,
            ),
    ) {
        Text(
            text = text,
        )
    }
}
