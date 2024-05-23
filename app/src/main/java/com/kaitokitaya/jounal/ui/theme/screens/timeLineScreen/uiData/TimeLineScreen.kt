package com.kaitokitaya.jounal.ui.theme.screens.timeLineScreen.uiData

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimeLineScreen() {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = "Time Line Screen",
            modifier = Modifier.padding(8.dp),
        )
    }
}
