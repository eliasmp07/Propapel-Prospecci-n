@file:OptIn(ExperimentalMaterial3Api::class)

package org.propapel.prospeccion.root.presentation.addlead.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import network.chaintech.kmp_date_time_picker.ui.datetimepicker.WheelDateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.TimeFormat
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import network.chaintech.kmp_date_time_picker.utils.dateTimeToString
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryPink
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.core.presentation.ui.typeHour
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.AddLeadState
import org.propapel.prospeccion.root.presentation.addlead.ContainerState
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.createProject.componetns.ExposedDropdownMenuGereric
import org.propapel.prospeccion.root.presentation.createProject.componetns.provideProductsPropapel
import org.propapel.prospeccion.root.presentation.createReminder.components.DialogDayNoAvailable
import org.propapel.prospeccion.root.presentation.createReminder.components.utils.TypeOfAppointment
import org.propapel.prospeccion.root.presentation.createReminder.components.utils.provideTypeOfAppointment

@Composable
fun AddInfoRemiderAppointmentScreen(
    modifier: Modifier = Modifier,
    state: AddLeadState,
    focusManager: FocusManager,
    onAction: (AddLeadAction) -> Unit
) {
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    val date = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    var selectedDate by remember { mutableStateOf("${date.dayOfMonth}-${date.monthNumber}-${date.year} ${date.hour}:${date.minute} ${typeHour(date.hour)}") }


    Column(
        modifier = modifier.background(
            Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF00BCD4), // Cian claro
                    Color(0xFF009688), // Verde azulado
                    Color(0xFF00796B)  // Verde intenso
                )
            )
        ).padding(16.dp).fillMaxSize().verticalScroll(rememberScrollState())
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
            text = "Informacion de la siguiente interaccion",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
        )
        KottieAnimationUtil(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(12.dp).aspectRatio(3f),
            fileRoute = "files/anim_add_info_reminder.json"
        )
        ProSalesTextField(
            title = "Fecha y hora:",
            readOnly = true,
            modifierTextField = Modifier.clickable {
                showDatePicker = true
            },
            colors = Color.White,
            state = selectedDate,
            onTextChange = {

            },
            startIcon = Icons.Filled.DateRange,
            maxLines = 104
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        var expandedProducts by remember {
            mutableStateOf(false)
        }
        ExposedDropdownMenuGereric(
            title = "Tipo de cita",
            state = expandedProducts,
            optionSelectable = state.typeAppointment,
            colors = Color.White,
            listOptions = provideTypeOfAppointment(),
            onDimiss = {
               expandedProducts = !expandedProducts
            },
            content = {
                DropdownMenuItem(
                    text = { androidx.compose.material.Text(text = it.toString()) },
                    onClick = {
                        expandedProducts = !expandedProducts
                        onAction(AddLeadAction.OnTypeAppointmentChange(it.name))
                    }
                )
            }
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        ProSalesTextField(
            title = "Notas para la proxima cita",
            state = state.descriptionNextAppointment,
            onTextChange = {
                onAction(AddLeadAction.OnDescriptionNextReminderChange(it))
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
            colors = Color.White,
            startIcon = Icons.Filled.Notes,
            maxLines = 104
        )
        Spacer(
            modifier = Modifier.weight(1f)
        )
        ProSalesActionButton(
            text = "Guardar",
            textColor = Color.White,
            isLoading = false,
            onClick = {
                onAction(AddLeadAction.OnNextScreenClick(ContainerState.FINISH))
            }
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
    }
    if (showDatePicker) {
        WheelDateTimePickerView(
            title = "Fecha y hora de la cita",
            modifier = Modifier.padding(
                top = 18.dp,
                bottom = 10.dp
            ).fillMaxWidth(),
            showDatePicker = showDatePicker,
            titleStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
            ),
            doneLabel = "Seleccionar",
            doneLabelStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = Color(0xFF007AFF),
            ),
            selectorProperties = WheelPickerDefaults.selectorProperties(
                borderColor = Color.LightGray,
            ),
            timeFormat = TimeFormat.AM_PM,
            dateTextColor = Color(0xff007AFF),
            rowCount = 5,
            height = 170.dp,
            dateTextStyle = TextStyle(
                fontWeight = FontWeight(600),
            ),
            onDoneClick = {
                onAction(AddLeadAction.OnDateNextReminder(localDateTimeToLong(it)))
                if (state.isDateAvailable) {
                    selectedDate = dateTimeToString(
                        it,
                        "dd-MM-yyyy hh:mm a"
                    )
                    showDatePicker = false
                }
            },
            dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
            onDismiss = {
                showDatePicker = false
            }
        )
    }

}


/**
 * Funcion que convierte el locaslDateTime a Long
 *
 * @param localDateTime local date time que se va a convertir
 * @return localdatetime ya convertido en formato long para validaciones
 */
fun localDateTimeToLong(localDateTime: LocalDateTime): Long {
    // Convierte LocalDateTime a Instant en la zona horaria UTC o la que prefieras
    val instant = localDateTime.toInstant(TimeZone.UTC)
    // Convierte Instant a milisegundos desde la época (Unix time)
    return instant.toEpochMilliseconds()
}