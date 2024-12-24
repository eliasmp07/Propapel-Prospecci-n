package org.propapel.prospeccion.root.presentation.createReminder.components.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.core.presentation.ui.typeHour
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.createProject.componetns.ExposedDropdownMenuGereric
import org.propapel.prospeccion.root.presentation.createReminder.CreateReminderAction
import org.propapel.prospeccion.root.presentation.createReminder.CreateReminderState
import org.propapel.prospeccion.root.presentation.createReminder.ExposedDropdownMenuCustomer
import org.propapel.prospeccion.root.presentation.createReminder.components.utils.provideTypeOfAppointment

@Composable
fun CreateReminderDesktopScreen(
    state: CreateReminderState,
    onAction: (CreateReminderAction) -> Unit,
) {
    val date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val focusManager = LocalFocusManager.current
    var selectedDate by remember { mutableStateOf("${date.dayOfMonth}-${date.monthNumber}-${date.year} ${date.hour}:${date.minute} ${typeHour(date.hour)}") }

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
    ){
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
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            /*

             */

            KottieAnimationUtil(
                modifier = Modifier.weight(0.5f).padding(12.dp).aspectRatio(3f),
                fileRoute = "files/anim_add_info_reminder.json"
            )
            Column(
                modifier = Modifier.weight(0.5f).verticalScroll(rememberScrollState())
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Datos de la cita",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(
                    modifier = Modifier.height(30.dp)
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
                    title = "Fecha y hora:",
                    readOnly = true,
                    modifierTextField = Modifier.clickable {

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
                    modifier = Modifier.height(32.dp)
                )
                ProSalesActionButton(
                    text = "Guardar",
                    textColor = Color.White,
                    shape = RoundedCornerShape(12.dp),
                    isLoading = state.isCreatingAppointment,
                    onClick = {
                        onAction(CreateReminderAction.CreateAppointmentClick)
                    }
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }

        }
    }

}