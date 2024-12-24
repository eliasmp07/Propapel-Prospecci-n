@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class,
            ExperimentalMaterial3WindowSizeClassApi::class
)

package org.propapel.prospeccion.root.presentation.createReminder

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import network.chaintech.kmp_date_time_picker.ui.datetimepicker.WheelDateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.TimeFormat
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import network.chaintech.kmp_date_time_picker.utils.dateTimeToString
import network.chaintech.kmp_date_time_picker.utils.now
import network.chaintech.kmp_date_time_picker.utils.timeToString
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.components.AdvancedTimePicker
import org.propapel.prospeccion.core.presentation.designsystem.components.DatePickerDialog
import org.propapel.prospeccion.core.presentation.designsystem.components.LoadingPropapel
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.core.presentation.designsystem.components.handleResultView
import org.propapel.prospeccion.core.presentation.designsystem.components.util.rememberImeState
import org.propapel.prospeccion.core.presentation.ui.typeHour
import org.propapel.prospeccion.root.presentation.addlead.components.localDateTimeToLong
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.createProject.componetns.ExposedDropdownMenuGereric
import org.propapel.prospeccion.root.presentation.createReminder.components.DialogCreateSuccess
import org.propapel.prospeccion.root.presentation.createReminder.components.DialogDayNoAvailable
import org.propapel.prospeccion.root.presentation.createReminder.components.desktop.CreateReminderDesktopScreen
import org.propapel.prospeccion.root.presentation.createReminder.components.utils.provideTypeOfAppointment
import org.propapel.prospeccion.root.presentation.dashboard.isMobile
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.empty_info
import prospeccion.composeapp.generated.resources.no_internet

@Composable
fun CreateReminderScreenRoot(
    viewModel: CreateReminderViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    CreateReminderScreen(
        state = state,
        onAction = { action ->
            when (action) {
                CreateReminderAction.OnBackClick -> onBack()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}


fun Int.getMouthString(): String{
   return when(this){
        1 -> "Enero"
       2 -> "Febrero"
       3 -> "Marzo"
       4 -> "Abril"
       5 -> "Mayo"
       6 -> "Junio"
       7 -> "Julio"
       8 -> "Agosto"
       9 -> "Septiembre"
       10 -> "Octubre"
       11 -> "Noviembre"
       else -> "Diciembre"
    }
}
@Composable
private fun CreateReminderScreen(
    state: CreateReminderState,
    onAction: (CreateReminderAction) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val windowSizeClass = calculateWindowSizeClass()
    val date = LocalDateTime.now()
    var selectedDate by remember { mutableStateOf("${date.dayOfMonth}-${date.monthNumber.getMouthString()}-${date.year}") }
    var timeSelected by remember {
        mutableStateOf(timeToString(date.time, "hh:mm a"))
    }
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }

    var showTimePicker by remember {
        mutableStateOf(false)
    }

    var showDialogPickerDate by remember {
        mutableStateOf(false)
    }
    val result = handleResultView(
        isLoading = state.isLoading,
        contentLoading = {
            LoadingPropapel()
        },
        error = state.error,
        isEmpty = state.customers.isEmpty(),
        contentEmpty = {
            Column(
                modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF00BCD4), // Cian claro
                            Color(0xFF009688), // Verde azulado
                            Color(0xFF00796B)  // Verde intenso
                        )
                    )
                ).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.End).padding(16.dp),
                    onClick = {
                        onAction(CreateReminderAction.OnBackClick)
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Image(
                    modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally),
                    painter = painterResource(Res.drawable.empty_info),
                    contentDescription = null
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                Text(
                    text = "Vaya no tienes ningun cliente, crear uno para poder crear una cita",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                ProSalesActionButtonOutline(
                    borderColor = Color.White,
                    textColor = Color.White,
                    text = "Regresar",
                    onClick = {
                        onAction(CreateReminderAction.OnBackClick)
                    }
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }
        },
        errorContent = {
            Column(
                modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF00BCD4), // Cian claro
                            Color(0xFF009688), // Verde azulado
                            Color(0xFF00796B)  // Verde intenso
                        )
                    )
                ).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.End).padding(16.dp),
                    onClick = {
                        onAction(CreateReminderAction.OnBackClick)
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Image(
                    modifier = Modifier.size(150.dp).align(Alignment.CenterHorizontally),
                    painter = painterResource(Res.drawable.no_internet),
                    contentDescription = null
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                Text(
                    text = it.asString(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                ProSalesActionButtonOutline(
                    borderColor = Color.White,
                    textColor = Color.White,
                    text = "Regresar",
                    onClick = {
                        onAction(CreateReminderAction.OnBackClick)
                    }
                )}
        }
    )

    if (result) {

        if (windowSizeClass.isMobile){
        Column(
            modifier = Modifier.fillMaxSize().background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF00BCD4), // Cian claro
                        Color(0xFF009688), // Verde azulado
                        Color(0xFF00796B)  // Verde intenso
                    )
                )
            ).padding(16.dp).verticalScroll(scrollState)
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.End).padding(16.dp),
                onClick = {
                    onAction(CreateReminderAction.OnBackClick)
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
                text = "Datos de la cita",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
            )
            KottieAnimationUtil(
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(12.dp).aspectRatio(3f),
                fileRoute = "files/anim_add_info_reminder.json"
            )
            ExposedDropdownMenuCustomer(
                title = "Lead(Cliente)",
                optionSelectable = state.customer,
                optionSelectableClick = {
                    onAction(CreateReminderAction.OnCustomerChange(it))
                },
                listOptions = state.customers
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
                state = selectedDate,
                onTextChange = {

                },
                startIcon = Icons.Filled.DateRange,
                maxLines = 104
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
                            onAction(CreateReminderAction.OnTypeAppointmentChange(it.name))
                        }
                    )
                }
            )
            ProSalesTextField(
                title = "Notas para la proxima cita",
                state = state.notesAppointment,
                onTextChange = {
                    onAction(CreateReminderAction.OnNoteAppointmentChange(it))
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
                startIcon = Icons.AutoMirrored.Filled.Notes,
                maxLines = 104
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            ProSalesActionButton(
                text = "Guardar",
                shape = RoundedCornerShape(12.dp),
                isLoading = state.isCreatingAppointment,
                onClick = {
                    onAction(CreateReminderAction.CreateAppointmentClick)
                }
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }}
        else{
            CreateReminderDesktopScreen(
                state = state,
                onAction = onAction
            )
        }
        if (showTimePicker){
            AdvancedTimePicker(
                onDismiss = {
                    showTimePicker = false
                },
                onConfirm = { long, string ->
                    onAction(CreateReminderAction.OnTimeNextReminder(long))
                    timeSelected = string
                }
            )
        }
        if (showDialogPickerDate) {
            DatePickerDialog(
                onDateSelected = { long, string ->
                    onAction(CreateReminderAction.OnDateNextReminder(long?:0))
                    selectedDate = string
                },
                onDismiss = {
                    showDialogPickerDate = !showDialogPickerDate
                }
            )
        }
    }

    if (state.isSuccessCreate) {
        DialogCreateSuccess(
            title = "Cita creada",
            message = "La cita se ha creado correctamente",
            onDismissRequest = {
                onAction(CreateReminderAction.OnBackClick)
            }
        )
    }

    if (state.dateNoAvailable){
        DialogDayNoAvailable(
            onDismissRequest = {
                onAction(CreateReminderAction.OnToggleDateNoAvailable)
            }
        )
    }


}