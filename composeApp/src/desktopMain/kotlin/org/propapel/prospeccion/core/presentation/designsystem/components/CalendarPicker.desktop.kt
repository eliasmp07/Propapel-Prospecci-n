@file:OptIn(ExperimentalMaterial3Api::class)

package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.root.presentation.createReminder.getMouthString
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@Composable
actual fun CalendarPicker(
    onDateSelected: (Long, String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val selectedDateMillis = Instant.fromEpochMilliseconds(datePickerState.selectedDateMillis?:0).toLocalDateTime(TimeZone.UTC)
                val formattedDate = "${selectedDateMillis.dayOfMonth}-${selectedDateMillis.monthNumber.getMouthString()}-${selectedDateMillis.year}"
                onDateSelected(datePickerState.selectedDateMillis?:0L, formattedDate)
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
