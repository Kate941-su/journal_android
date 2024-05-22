package com.kaitokitaya.jounal.ui.theme.screens.sharedComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import com.kaitokitaya.jounal.R
import com.kaitokitaya.jounal.type_define.VoidCallback

@Composable
fun JournalAppDropDownMenu(
    isExpanded: Boolean,
    onDismissRequest: VoidCallback,
    onSave: VoidCallback,
    onDelete: VoidCallback,
) {
    val context = LocalContext.current
    // Why I quit to get the list item argument and define specifically here is that each item in the drop down menu
    // has an original functionality and it's difficult to fix type.
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopEnd),
    ) {
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = onDismissRequest,
        ) {
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_save),
                        contentDescription = "Save",
                    )
                },
                text = { Text("Save") },
                onClick = onSave,
            )
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Save",
                    )
                },
                text = { Text("Delete") },
                onClick = onDelete,
            )
        }
    }
}
