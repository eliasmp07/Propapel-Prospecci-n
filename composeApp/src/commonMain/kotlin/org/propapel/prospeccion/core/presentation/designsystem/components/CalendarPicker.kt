package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

@Composable
expect fun CalendarPicker(
    onDateSelected: (Long, String) -> Unit,
    onDismiss :() -> Unit
)