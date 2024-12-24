@file:OptIn(ExperimentalMaterial3Api::class)

package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.material3.TimePickerState


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import network.chaintech.kmp_date_time_picker.utils.timeToString
import org.propapel.prospeccion.root.presentation.createReminder.convertLocalDate
import java.util.Calendar

@Composable
actual fun AdvancedTimePicker(
    onConfirm: (Long, String) -> Unit,
    onDismiss: () -> Unit
) {
    var time by remember {
        mutableStateOf(0L)
    }
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    /** Determines whether the time picker is dial or input */
    var showDial by remember { mutableStateOf(true) }

    /** The icon used for the icon button that switches from dial to input */
    val toggleIcon = if (showDial) {
        Icons.Filled.EditCalendar
    } else {
        Icons.Filled.AccessTime
    }

    AdvancedTimePickerDialog(
        onDismiss = { onDismiss() },
        onConfirm = {
            val selectedTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                set(Calendar.MINUTE, timePickerState.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val timeInMillis = selectedTime.timeInMillis // Convertir a long
            val localDateTime = convertLocalDate(selectedTime.timeInMillis).time
            onConfirm(timeInMillis, timeToString(localDateTime, "hh:mm a")) // Enviar el valor de tiempo en milisegundos
                    },
        toggle = {
            IconButton(onClick = { showDial = !showDial }) {
                Icon(
                    imageVector = toggleIcon,
                    contentDescription = "Time picker type toggle",
                )
            }
        },
    ) {
        if (showDial) {
            TimePicker(
                state = timePickerState,
            )
        } else {
            TimeInput(
                state = timePickerState,
            )
        }
    }
}