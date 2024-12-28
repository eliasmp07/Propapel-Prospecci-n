@file:OptIn(ExperimentalMaterial3Api::class)

package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaLocalDateTime
import org.propapel.prospeccion.core.presentation.ui.extensions.toLocalDateTime
import org.propapel.prospeccion.root.presentation.createReminder.convertLocalDate
import org.propapel.prospeccion.root.presentation.createReminder.getMouthString

@Composable
actual fun DatePickerDialog(
    onDateSelected: (Long?, String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    androidx.compose.material3.DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val date = datePickerState.selectedDateMillis?.toLocalDateTime()
                onDateSelected(
                    datePickerState.selectedDateMillis,
                    "${date?.dayOfMonth}-${date?.monthNumber?.getMouthString()}-${date?.year?:""}"
                )
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}