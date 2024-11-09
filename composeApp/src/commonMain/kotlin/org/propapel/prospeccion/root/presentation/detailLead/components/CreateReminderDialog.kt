package org.propapel.prospeccion.root.presentation.detailLead.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.window.Dialog
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import network.chaintech.kmp_date_time_picker.ui.datetimepicker.WheelDateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.TimeFormat
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import network.chaintech.kmp_date_time_picker.utils.dateTimeToString
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButtonOutline
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.core.presentation.ui.typeHour
import org.propapel.prospeccion.root.presentation.addlead.AddLeadAction
import org.propapel.prospeccion.root.presentation.addlead.components.localDateTimeToLong
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadAction
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadSMState

@Composable
fun CreateReminderDialog(
    modifier: Modifier = Modifier,
    state: DetailLeadSMState,
    onAction: (DetailLeadAction) -> Unit,
    onDismissRequest: () -> Unit
) {
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    val date = Clock.System.now().toLocalDateTime(TimeZone.UTC)
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
                        text = "Crear cita",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Datos de la proxima cita",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    ProSalesTextField(
                        title = "Fecha y hora:",
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
                            onAction(DetailLeadAction.CreateAppointmentClick)
                        }
                    )
                }
            }
        }
    )
    if (showDatePicker) {
        WheelDateTimePickerView(
            title = "Fecha y hora de la cita",
            modifier = Modifier.padding(top = 18.dp, bottom = 10.dp).fillMaxWidth(),
            showDatePicker = showDatePicker,
            titleStyle = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
            ),
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
                onAction(DetailLeadAction.OnDateNextReminder(localDateTimeToLong(it)))
                selectedDate = dateTimeToString(it, "dd-MM-yyyy hh:mm a")
                showDatePicker = false
            },
            dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
            onDismiss = {
                showDatePicker = false
            }
        )
    }
}