package org.propapel.prospeccion.root.presentation.addlead.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.AddLeadState
import org.propapel.prospeccion.root.presentation.addlead.ContainerState
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil

@Composable
fun AddNewDateClientScreen(
    modifier: Modifier = Modifier,
    state: AddLeadState,
    onAction: (AddLeadAction) -> Unit
) {

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    // Extraer el mes actual y el año
    val currentMonth = currentDateTime.month
    val currentYear = currentDateTime.year

    var date by remember {
        mutableStateOf("${currentDateTime.dayOfMonth} / ${currentMonth.number} / $currentYear\"")
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Informacion de la cita",
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.headlineSmall,
        )
        KottieAnimationUtil(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(12.dp).aspectRatio(3f),
            fileRoute = "files/anim_appointment.json"
        )
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        ProSalesTextField(
            title = "Fecha de la visita del dia de hoy",
            readOnly = true,
            state = "${currentDateTime.dayOfMonth}/${currentMonth.number}/$currentYear",
            onTextChange = {

            },
            startIcon = Icons.Filled.DateRange,
            maxLines = 104
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        ExposedDropdownMenuTypeAppointment(
            title = "Tipo de cita",
            listOptions = listOf(
                InteractionType.EMAIL, InteractionType.LLAMADA, InteractionType.REUNION_REMOTA, InteractionType.PRESENCIAL
            ),
            optionSelectable = state.typeDate,
            optionSelectableClick = {
                onAction(AddLeadAction.OnTypeDateChange(it))
            }
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        ProSalesTextField(
            title = "Comentarios sobre la cita",
            state = state.notes,
            onTextChange = {
                onAction(AddLeadAction.OnNoteChange(it))
            },
            startIcon = Icons.Filled.Notes,
            maxLines = 104
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        ProSalesActionButton(
            text = "Guardar",
            isLoading = false,
            onClick = {
                onAction(AddLeadAction.OnNextScreenClick(ContainerState.HE_IS_INTERESTED_IN_A_PRODUCT))
            }
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
    }
    if (showDatePicker) {
        DatePickerDialog(
            onDateSelected = { date = it },
            onDateSelectedChange = {

            },
            onDismiss = { showDatePicker = false }
        )
    }
}

/*
 Text(text = "¿Quieres recordar algun pendiente?", style = MaterialTheme.typography.bodyMedium)
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Row(Modifier.selectableGroup().fillMaxWidth(), horizontalArrangement = Arrangement.Center,  verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = state.hasPendients,
                onClick = {
                    onAction(AddLeadAction.OnSelectHasPendientDateClick)
                },
                modifier = Modifier.semantics { contentDescription = "Localized Description" }
            )
            Text(text = "Si")
            RadioButton(
                selected = !state.hasPendients,
                onClick = {
                    onAction(AddLeadAction.OnSelectHasPendientDateClick)
                },
                modifier = Modifier.semantics { contentDescription = "Localized Description" }
            )
            Text(text = "No")
        }
        AnimatedVisibility(
            visible = state.hasPendients
        ){
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                ProSalesTextField(
                    state = state.task,
                    startIcon = null,
                    endIcon = Icons.Rounded.EditNote,
                    hint = "Ejemplo: Cotizar impresora",
                    title = "Tareas o pendientes",
                    onTextChange = {
                        onAction(AddLeadAction.OnTaskChange(it))
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onAction(AddLeadAction.OnSaveTaskClick)
                            focus.clearFocus()
                        }
                    )
                )
                LazyColumn {
                    items(state.tasks) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.TaskAlt,
                                    contentDescription = "Tarea o pendiente"
                                )
                                Spacer(
                                    modifier = Modifier.width(8.dp)
                                )
                                Text(text = it, style = MaterialTheme.typography.bodyMedium)
                                Spacer(
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = {
                                        onAction(AddLeadAction.OnRemoveTaskClick(it))
                                    },
                                    content = {
                                        Icon(
                                            imageVector = Icons.Rounded.Delete,
                                            contentDescription = null
                                        )
                                    }
                                )
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
 */