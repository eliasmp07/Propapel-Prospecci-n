package org.propapel.prospeccion.root.presentation.completeReminder.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryViolet
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryVioletDark
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesActionButton
import org.propapel.prospeccion.core.presentation.designsystem.components.ProSalesTextField
import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.presentation.addlead.components.ExposedDropdownMenuTypeAppointment
import org.propapel.prospeccion.root.presentation.addlead.components.utils.KottieAnimationUtil
import org.propapel.prospeccion.root.presentation.completeReminder.CompleteReminderAction
import org.propapel.prospeccion.root.presentation.completeReminder.CompleteReminderState

@Composable
fun AddInfoInterviewReminder(
    modifier: Modifier = Modifier,
    state: CompleteReminderState,
    onAction: (CompleteReminderAction) -> Unit
) {

    val currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    // Extraer el mes actual y el año
    val currentMonth = currentDateTime.month
    val currentYear = currentDateTime.year

    var date by remember {
        mutableStateOf("${currentDateTime.dayOfMonth} / ${currentMonth.number} / $currentYear\"")
    }

    Column(
        modifier = modifier.fillMaxSize().background(
            Brush.verticalGradient(
                0f to PrimaryViolet,
                1f to PrimaryVioletDark
            )
        ).padding(16.dp)
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.End).padding(16.dp),
            onClick = {
                onAction(CompleteReminderAction.OnBackClick)
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
            text = "Informacion de la cita",
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
            title = "Fecha de la visita del dia de hoy",
            readOnly = true,
            colors = Color.White,
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
                onAction(CompleteReminderAction.OnTypeDateChange(it))
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
                onAction(CompleteReminderAction.OnNoteChange(it))
            },
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
                onAction(CompleteReminderAction.OnNextScreenClick(CompleteReminderScreenState.HE_IS_INTERESTED_A_PRODUCT))
            }
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
    }
}