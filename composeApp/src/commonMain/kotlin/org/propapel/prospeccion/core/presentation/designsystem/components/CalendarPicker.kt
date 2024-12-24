@file:OptIn(ExperimentalMaterial3Api::class)

package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import network.chaintech.kmp_date_time_picker.utils.dateTimeToString
import network.chaintech.kmp_date_time_picker.utils.timeToString
import org.propapel.prospeccion.root.presentation.createReminder.convertLocalDate
import org.propapel.prospeccion.root.presentation.createReminder.getMouthString

@Composable
expect fun DatePickerDialog(
    onDateSelected: (Long?, String) -> Unit,
    onDismiss: () -> Unit
)