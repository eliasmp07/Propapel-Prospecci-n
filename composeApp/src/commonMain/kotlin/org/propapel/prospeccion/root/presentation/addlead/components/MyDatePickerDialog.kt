@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
            ExperimentalMaterial3Api::class
)

package org.propapel.prospeccion.root.presentation.addlead.components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.root.presentation.addlead.AddLeadState

@Composable
fun DatePickerDialog(
    state: AddLeadState = AddLeadState(),
    onDateSelected: (String) -> Unit,
    onDateSelectedChange: (Long) -> Unit,
    onDismiss: () -> Unit
) {

    val datesOcupados = state.reminders.map { it.reminderDate.toLong()} // Supongo que tienes un campo llamado remiderDate

    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= Clock.System.now().toEpochMilliseconds() - 86400000
            }
        }
    )

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDateSelectedChange(datePickerState.selectedDateMillis ?: 0)
                onDismiss()
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val instant = Instant.fromEpochMilliseconds(millis)
    val localDateTime: LocalDateTime = instant.toLocalDateTime(TimeZone.UTC)
    return "${localDateTime.dayOfMonth.toString().padStart(2, '0')}/" +
            "${localDateTime.monthNumber.toString().padStart(2, '0')}/" +
            localDateTime.year
}
