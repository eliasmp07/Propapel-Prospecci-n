package org.propapel.prospeccion.root.presentation.detailLead.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.core.presentation.ui.typeHour
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadAction
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadSMState

@Composable
fun DialogDeleteProject(
    modifier: Modifier = Modifier,
    state: DetailLeadSMState,
    onAction: (DetailLeadAction) -> Unit,
    onDismissRequest: () -> Unit
) {
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    val date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    var selectedDate by remember { mutableStateOf("${date.dayOfMonth}-${date.monthNumber}-${date.year} ${date.hour}:${date.minute} ${typeHour(date.hour)}") }

    val focusManager = LocalFocusManager.current
    Dialog(
        onDismissRequest = onDismissRequest,
        content = {
            ElevatedCard(
                modifier = modifier
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    IconButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {
                            onDismissRequest()
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Eliminar proyecto",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Motivos de eliminacion de proyecto",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    ProSalesTextField(
                        title = "ewewe:",
                        readOnly = true,
                        modifierTextField = Modifier.clickable {
                            showDatePicker = true
                        },
                        colors = Color.Black,
                        state = selectedDate,
                        onTextChange = {

                        },
                        startIcon = Icons.Filled.DateRange,
                        maxLines = 104
                    )
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                    ProSalesTextField(
                        title = "Notas para la proxima cita",
                        state = state.notesAppointment,
                        onTextChange = {
                            onAction(DetailLeadAction.OnNoteAppointmentChange(it))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.clearFocus()
                            }
                        ),
                        colors = Color.Black,
                        startIcon = Icons.Filled.Notes,
                        maxLines = 104
                    )
                    Spacer(Modifier.height(8.dp))
                    ProSalesActionButtonOutline(
                        text = "Crear cita",
                        isLoading = state.isCreatingAppointment,
                        onClick = {
                            focusManager.clearFocus()
                            onAction(DetailLeadAction.CreateAppointmentClick)
                        }
                    )
                }
            }
        }
    )
}