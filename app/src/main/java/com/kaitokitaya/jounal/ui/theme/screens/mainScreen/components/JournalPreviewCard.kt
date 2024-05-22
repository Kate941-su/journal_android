package com.kaitokitaya.jounal.ui.theme.screens.mainScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@Composable
fun JournalPreviewCard(
    title: String,
    date: LocalDate,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable { },
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 10.dp,
            ),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.AccountCircle, contentDescription = "", modifier = Modifier.padding(16.dp))
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
                Text(text = "$date")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun JournalPreviewCardPreview() {
    JournalPreviewCard(title = "Happy Day", date = LocalDate.now())
}
