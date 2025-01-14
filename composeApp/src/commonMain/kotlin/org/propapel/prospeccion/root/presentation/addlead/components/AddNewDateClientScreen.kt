@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package org.propapel.prospeccion.root.presentation.addlead.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import network.chaintech.kmp_date_time_picker.utils.now
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryViolet
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryVioletDark
import org.propapel.prospeccion.core.presentation.designsystem.components.AdvancedTimePicker
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.core.presentation.ui.extensions.toFormatStringDate
import org.propapel.prospeccion.core.presentation.ui.extensions.toFormatStringTime
import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.AddLeadState
import org.propapel.prospeccion.root.presentation.addlead.ContainerState
import org.propapel.prospeccion.root.presentation.addlead.GenericContentWindowsSize
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.dashboard.isMobile
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.calendar_date
import prospeccion.composeapp.generated.resources.date_mark
import prospeccion.composeapp.generated.resources.user_info

@Composable
fun AddNewDateClientScreen(
    modifier: Modifier = Modifier,
    state: AddLeadState,
    onAction: (AddLeadAction) -> Unit
) {
    var showInformation by remember {
        mutableStateOf(false)
    }
    val defaultTimeAndDate =  remember {
        mutableStateOf(true)
    }
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
                onAction(AddLeadAction.OnBackClick)
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
            text = "Informacion de la interacion",
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
                if (!defaultTimeAndDate.value){
                    showTimePicker = !showTimePicker
                }else{
                    showInformation =!showInformation
                }
            },
            colors = Color.White,
            state = if(defaultTimeAndDate.value) date.toFormatStringTime() else timeSelected,
            onTextChange = {

            },
            startIcon = Icons.Filled.Timer,
            maxLines = 104

        )
        ProSalesTextField(
            title = "Fecha:",
            readOnly = true,
            modifierTextField = Modifier.clickable {
                if (!defaultTimeAndDate.value){
                    showDialogPickerDate = !showDialogPickerDate
                }else{
                    showInformation = !showInformation
                }
            },
            colors = Color.White,
            state =if(defaultTimeAndDate.value) date.toFormatStringDate() else  dateSelected,
            onTextChange = {

            },
            startIcon = Icons.Filled.DateRange,
            maxLines = 104
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Checkbox(
                enabled = true,
                checked = defaultTimeAndDate.value,
                onCheckedChange = {
                    defaultTimeAndDate.value = it
                }
            )
            Text(
                text = "Usar la fecha de hoy",
                color = Color.White,
                style = MaterialTheme.typography.labelMedium
            )
        }
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        ExposedDropdownMenuTypeAppointment(
            title = "Tipo de interaccion",
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
            colors = Color.White,
            state = state.notes,
            onTextChange = {
                onAction(AddLeadAction.OnNoteChange(it))
            },
            startIcon = Icons.AutoMirrored.Filled.Notes,
            maxLines = 104,
            keyboardOptions = KeyboardOptions(
                imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
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
    if (showTimePicker){
        AdvancedTimePicker(
            onDismiss = {
                showTimePicker = false
            },
            onConfirm = { long, string ->
               onAction(AddLeadAction.OnTimeModifierChange(long))
                timeSelected = string
            }
        )
    }
    if (showDialogPickerDate) {
        org.propapel.prospeccion.core.presentation.designsystem.components.DatePickerDialog(
            onDateSelected = { long, string ->
                onAction(AddLeadAction.OnDateNextReminder(long ?: 0))
                dateSelected = string
            },
            onDismiss = {
                showDialogPickerDate = !showDialogPickerDate
            }
        )
    }
    if (showInformation){
        IsUsingDateActual {
            showInformation = false
        }
    }
}

@Composable
fun IsUsingDateActual(
    onDismissRequest: () -> Unit,
){
    Dialog(
        onDismissRequest = onDismissRequest
    ){
        ElevatedCard {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Información",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Estas usando la fecha del dia de hoy, desmarca el campo para poder configurar la fecha",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium
                )
                Image(
                    painter = painterResource(Res.drawable.date_mark),
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    contentDescription = null
                )
                ProSalesActionButton(
                    text = "Okey",
                    shape = RoundedCornerShape(12.dp),
                    onClick = onDismissRequest
                )
            }
        }
    }
}