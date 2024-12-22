package org.propapel.prospeccion.root.presentation.dates

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import network.chaintech.kmp_date_time_picker.utils.withDayOfMonth
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.ui.extensions.formatToMonthString
import org.propapel.prospeccion.root.domain.models.Reminder
import org.propapel.prospeccion.root.presentation.createReminder.convertLocalDate
import org.propapel.prospeccion.root.presentation.dashboard.components.ItemUserDate
import org.propapel.prospeccion.root.presentation.dashboard.isMobile
import org.propapel.prospeccion.root.presentation.dates.components.desktop.DatesScreenDesktop
import org.propapel.prospeccion.root.presentation.dates.components.mobile.CalendarGrid
import org.propapel.prospeccion.root.presentation.dates.components.mobile.DatesScreenMobile
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.empty_info

@Composable
fun DateScreenRoot(
    windowWidthSizeClass: WindowSizeClass,
    viewModel: DatesSMViewModel,
    onDetailReminderCustomer : (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    DateScreen(
        windowWidthSizeClass,
        state = state,
        onAction = { action ->
            when(action){
                is DatesAction.OnDetailReminder -> {
                    onDetailReminderCustomer(action.reminderId)
                }
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun DateScreen(
    windowWidthSizeClass: WindowSizeClass,
    state: DatesSMState,
    onAction: (DatesAction) -> Unit
){
    if (windowWidthSizeClass.isMobile){
        DatesScreenMobile(
            state = state,
            onAction = onAction
        )
    } else {
        DatesScreenDesktop(
            state = state,
            onAction = onAction
        )
    }
}

@Composable
fun DisplayAppointments(
    date: LocalDate,
    dates: List<LocalDate>,
    onDetailReminderCustomer: (String) -> Unit,
    reminders: List<Reminder>
) {

    val appointments = dates.filter { it == date }

    val appointmentsReminder = reminders.filter { convertLocalDate(it.reminderDate.toLong()).date == date }

    Column(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Citas para ${date.dayOfMonth} de ${date.formatToMonthString()}:",
            fontWeight = FontWeight.Bold,
            style = typography.titleMedium,
            textAlign = TextAlign.Center,
            color = colorScheme.onPrimaryContainer,
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        if (appointments.isEmpty()) {
            Spacer(
                modifier = Modifier.height(8.dp)
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
                text = "No tienes ningun cita programada para este día",
                style = typography.titleMedium,
                textAlign = TextAlign.Center,
                color = colorScheme.onPrimaryContainer,
            )

        } else {
            appointmentsReminder.forEach { reminder ->
                ItemUserDate(
                    reminder = reminder,
                    onDetailReminder = {
                        onDetailReminderCustomer(reminder.reminderId.toString())
                    }
                )
            }
        }
    }
}


@Composable
fun CalendarView(
    month: LocalDate,
    datesReminder: List<LocalDate>,
    date: List<Pair<LocalDate, Boolean>>?,  // Cambiado a List en lugar de ImmutableList
    displayNext: Boolean,
    displayPrev: Boolean,
    onClickNext: () -> Unit,
    onClickPrev: () -> Unit,
    onClick: (LocalDate) -> Unit,
    startFromSunday: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth()) {
            if (displayPrev)
                IconButton(
                    onClick = onClickPrev,
                    modifier = Modifier.align(Alignment.CenterStart),
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    },
                )
            if (displayNext)
                IconButton(
                    onClick = onClickNext,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )
                    },
                )
            Text(
                text = "${month.formatToMonthString()} ${month.year}",
                style = typography.headlineMedium,
                color = colorScheme.onPrimaryContainer,
                modifier = Modifier.align(Alignment.Center),
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        if (!date.isNullOrEmpty()) {
            CalendarGrid(
                date = date,  // Se pasa la lista mutable a CalendarGrid
                onClick = onClick,
                datesReminder = datesReminder,
                startFromSunday = startFromSunday,
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}
