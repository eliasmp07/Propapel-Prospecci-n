@file:OptIn(ExperimentalMaterial3Api::class)

package org.propapel.prospeccion.root.presentation.addlead.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TimePickerDialog(
    onConfirm: (Long) -> Unit,
    onSelectedHour: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    val timePickerState = rememberTimePickerState(
        initialHour = currentDateTime.hour,
        initialMinute = currentDateTime.minute,
        is24Hour = true,
    )
    Box(
        modifier = Modifier.fillMaxSize().background(
            Color.Black.copy(alpha = 0.5f)
        )
    ){
        Column(
            modifier = Modifier.align(Alignment.Center).clip(RoundedCornerShape(12.dp)).background(Color.White).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Selecione la hora", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            TimePicker(
                state = timePickerState,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = onDismiss) {
                    Text("Cancelar")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    onSelectedHour(
                        "${timePickerState.hour}:${timePickerState.minute}"
                    )
                    onConfirm(timePickerState.hour.toLong()+timePickerState.minute.toLong())
                }) {
                    Text("Confimar")
                }
            }
        }
    }

}