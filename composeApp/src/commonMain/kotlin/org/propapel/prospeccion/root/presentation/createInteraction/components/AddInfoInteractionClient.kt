package org.propapel.prospeccion.root.presentation.createInteraction.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import network.chaintech.kmp_date_time_picker.utils.now
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryViolet
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryVioletDark
import org.propapel.prospeccion.core.presentation.designsystem.components.AdvancedTimePicker
import org.propapel.prospeccion.core.presentation.designsystem.components.DatePickerDialog
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.core.presentation.ui.extensions.toFormatStringDate
import org.propapel.prospeccion.core.presentation.ui.extensions.toFormatStringTime
import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.presentation.addlead.GenericContentWindowsSize
import org.propapel.prospeccion.root.presentation.addlead.components.ExposedDropdownMenuTypeAppointment
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.createInteraction.CreateInteractionAction
import org.propapel.prospeccion.root.presentation.createInteraction.CreateInteractionLeadState
import org.propapel.prospeccion.root.presentation.createInteraction.CreateInteractionScreenState
import org.propapel.prospeccion.root.presentation.createReminder.CreateReminderAction
import org.propapel.prospeccion.root.presentation.dashboard.components.monthGet
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.calendar_date

@Composable
fun AddInfoInteractionClient(
    modifier: Modifier = Modifier,
    state: CreateInteractionLeadState,
    isMobile: Boolean,
    onAction: (CreateInteractionAction) -> Unit
) {
    val date = LocalDateTime.now()

    var showTimePicker by remember {
        mutableStateOf(false)
    }

    var showDialogPickerDate by remember {
        mutableStateOf(false)
    }

    var timeSelected by remember {
        mutableStateOf(date.toFormatStringTime())
    }

    var dateSelected by remember {
        mutableStateOf(date.toFormatStringDate())
    }
    val focusManager = LocalFocusManager.current
    if (isMobile) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).background(
                Brush.verticalGradient(
                    0f to PrimaryViolet,
                    1f to PrimaryVioletDark
                )
            ).padding(16.dp)
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.End).padding(16.dp),
                onClick = {
                    onAction(CreateInteractionAction.OnBackClick)
                },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Informacion de la actividad",
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            KottieAnimationUtil(
                modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally),
                fileRoute = "files/anim_interview.json"
            )
            Spacer(
                modifier = Modifier.height(32.dp)
            )
            ProSalesTextField(
                title = "Hora:",
                readOnly = true,
                modifierTextField = Modifier.clickable {
                    showTimePicker = !showTimePicker
                },
                colors = Color.White,
                state = timeSelected,
                onTextChange = {

                },
                startIcon = Icons.Filled.Timer,
                maxLines = 104

            )
            ProSalesTextField(
                title = "Fecha:",
                readOnly = true,
                modifierTextField = Modifier.clickable {
                    showDialogPickerDate = !showDialogPickerDate
                },
                colors = Color.White,
                state = dateSelected,
                onTextChange = {

                },
                startIcon = Icons.Filled.DateRange,
                maxLines = 104
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            ExposedDropdownMenuTypeAppointment(
                title = "Tipo de interaccion",
                listOptions = listOf(
                    InteractionType.EMAIL,
                    InteractionType.LLAMADA,
                    InteractionType.REUNION_REMOTA,
                    InteractionType.PRESENCIAL
                ),
                optionSelectable = state.typeClient,
                optionSelectableClick = {
                    onAction(CreateInteractionAction.OnTypeClientChange(it))
                }
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            ProSalesTextField(
                title = "Comentarios",
                colors = Color.White,
                state = state.notesAppointment,
                onTextChange = {
                    onAction(CreateInteractionAction.OnNoteAppointmentChange(it))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                startIcon = Icons.AutoMirrored.Filled.Notes,
                maxLines = 104
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            ProSalesActionButton(
                text = "Guardar",
                isLoading = false,
                onClick = {
                    onAction(CreateInteractionAction.OnNextScreen(CreateInteractionScreenState.He_Client_Intersed_A_Product))
                }
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }
    } else {
        GenericContentWindowsSize(
            onCloseScreen = {
                onAction(CreateInteractionAction.OnBackClick)
            },
            content1 = {
                Image(
                    modifier = Modifier.weight(0.5f).aspectRatio(3f),
                    painter = painterResource(
                        Res.drawable.calendar_date
                    ),
                    contentDescription = null
                )
            },
            brush = Brush.verticalGradient(
                0f to PrimaryViolet,
                1f to PrimaryVioletDark
            ),
            content2 = {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Informacion de la actividad",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
                Spacer(
                    modifier = Modifier.height(32.dp)
                )
                ProSalesTextField(
                    title = "Fecha de la visita de la actividad",
                    readOnly = true,
                    colors = Color.White,
                    state = "${state.date.dayOfMonth}/${monthGet(state.date.monthNumber)}/${state.date.year}",
                    onTextChange = {

                    },
                    startIcon = Icons.Filled.DateRange,
                    maxLines = 104
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                ExposedDropdownMenuTypeAppointment(
                    title = "Tipo de interaccion",
                    listOptions = listOf(
                        InteractionType.EMAIL,
                        InteractionType.LLAMADA,
                        InteractionType.REUNION_REMOTA,
                        InteractionType.PRESENCIAL
                    ),
                    optionSelectable = state.typeClient,
                    optionSelectableClick = {
                        onAction(CreateInteractionAction.OnTypeClientChange(it))
                    }
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                ProSalesTextField(
                    title = "Comentarios",
                    colors = Color.White,
                    state = state.notesAppointment,
                    onTextChange = {
                        onAction(CreateInteractionAction.OnNoteAppointmentChange(it))
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    startIcon = Icons.AutoMirrored.Filled.Notes,
                    maxLines = 104
                )
                Spacer(
                    modifier = Modifier.height(32.dp)
                )
                ProSalesActionButton(
                    text = "Guardar",
                    isLoading = false,
                    onClick = {
                        onAction(CreateInteractionAction.OnNextScreen(CreateInteractionScreenState.He_Client_Intersed_A_Product))
                    }
                )
            }
        )
    }
    if (showTimePicker){
        AdvancedTimePicker(
            onDismiss = {
                showTimePicker = false
            },
            onConfirm = { long, string ->
               onAction(CreateInteractionAction.OnTimeNextReminder(long))
                timeSelected = string
            }
        )
    }
    if (showDialogPickerDate) {
        DatePickerDialog(
            onDateSelected = { long, string ->
                onAction(CreateInteractionAction.OnDateNextReminder(long?:0))
                dateSelected = string
            },
            onDismiss = {
                showDialogPickerDate = !showDialogPickerDate
            }
        )
    }
}