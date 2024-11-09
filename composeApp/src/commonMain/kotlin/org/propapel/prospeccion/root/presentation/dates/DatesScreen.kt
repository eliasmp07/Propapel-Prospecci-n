package org.propapel.prospeccion.root.presentation.dates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import network.chaintech.kmp_date_time_picker.utils.withDayOfMonth
import org.jetbrains.compose.resources.painterResource
import org.propapel.prospeccion.core.presentation.designsystem.PrimaryYellowLight
import org.propapel.prospeccion.core.presentation.designsystem.SoporteSaiBlue30
import org.propapel.prospeccion.root.domain.models.Reminder
import org.propapel.prospeccion.root.presentation.createReminder.convertLocalDate
import org.propapel.prospeccion.root.presentation.dashboard.components.ItemUserDate
import org.propapel.prospeccion.root.presentation.dashboard.isMobile
import org.propapel.prospeccion.root.presentation.dates.components.CalendarDateSelector
import org.propapel.prospeccion.root.presentation.dates.components.desktop.DatesScreenDesktop
import org.propapel.prospeccion.root.presentation.dates.components.mobile.CalendarGrid
import org.propapel.prospeccion.root.presentation.dates.components.mobile.DatesScreenMobile
import prospeccion.composeapp.generated.resources.Res
import prospeccion.composeapp.generated.resources.empty_info

@Composable
fun DateScreenRoot(
    windowWidthSizeClass: WindowSizeClass,
    viewModel: DatesSMViewModel
) {
    val state by viewModel.state.collectAsState()
    DateScreen(
        windowWidthSizeClass,
        state = state
    )
}

fun LocalDate.plusMonths(months: Int): LocalDate {
    var newMonth = this.monthNumber + months
    var newYear = this.year

    // Ajustar el año y el mes si es necesario
    if (newMonth > 12) {
        newYear += newMonth / 12
        newMonth %= 12
    } else if (newMonth < 1) {
        newYear += (newMonth - 12) / 12
        newMonth = 12 + newMonth % 12
    }

    // Asegurarse de que el día sea válido
    val daysInNewMonth = getDaysInMonth(
        newYear,
        newMonth
    )
    val newDay = this.dayOfMonth.coerceAtMost(daysInNewMonth)

    return LocalDate(
        newYear,
        newMonth,
        newDay
    )
}

fun LocalDate.minusMonths(months: Int): LocalDate {
    return this.plusMonths(-months)
}

private fun getDaysInMonth(
    year: Int,
    month: Int
): Int {
    return when (month) {
        1 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28 // Enero
        2 -> 31 // Febrero
        3 -> 31 // Marzo
        4 -> 30 // Abril
        5 -> 31 // Mayo
        6 -> 30 // Junio
        7 -> 31 // Julio
        8 -> 31 // Agosto
        9 -> 30 // Septiembre
        10 -> 31 // Octubre
        11 -> 30 // Noviembre
        12 -> 31 // Diciembre
        else -> 0 // Si el mes no es válido
    }
}

@Composable
private fun DateScreen(
    windowWidthSizeClass: WindowSizeClass,
    state: DatesSMState
){
    if (windowWidthSizeClass.isMobile){
        DatesScreenMobile(
            state = state
        )
    } else {
        DatesScreenDesktop()
    }
}

@Composable
fun DisplayAppointments(
    date: LocalDate,
    dates: List<LocalDate>,
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
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = Color.White
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
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = Color.White
            )

        } else {
            appointmentsReminder.forEach { reminder ->
                ItemUserDate(
                    reminder = reminder,
                    onDetailReminder = {

                    }
                )
            }
        }
    }
}


fun generateDatesForMonth(
    month: LocalDate,
    startFromSunday: Boolean
): List<Pair<LocalDate, Boolean>> {
    val firstDayOfMonth = month.withDayOfMonth(1)
    val daysInMonth = getDaysInMonth(
        firstDayOfMonth.year,
        firstDayOfMonth.monthNumber
    )

    // Obtener el día de la semana del primer día del mes (0 = Lunes, 6 = Domingo)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.ordinal

    // Crea una lista mutable para las fechas
    val dates = mutableListOf<Pair<LocalDate, Boolean>>()

    // Calcular cuántos días agregar del mes anterior
    val previousMonth = firstDayOfMonth.minusMonths(1)
    val daysInPreviousMonth = getDaysInMonth(
        previousMonth.year,
        previousMonth.monthNumber
    )

    // Determina el índice inicial para la lista de días
    val startIndex = if (startFromSunday) {
        if (firstDayOfWeek == 0) 6 else firstDayOfWeek - 1 // Ajuste para domingo
    } else {
        firstDayOfWeek // Comenzar desde lunes
    }

    // Agregar días del mes anterior
    for (i in startIndex downTo 1) {
        val date = previousMonth.withDayOfMonth(daysInPreviousMonth - i + 1)
        dates.add(date to false)
    }

    // Agregar días del mes actual
    for (day in 1..daysInMonth) {
        // Verificar si el día es 29 de febrero y si el año es bisiesto
        if (firstDayOfMonth.monthNumber == 2 && day == 29 && !isLeapYear(firstDayOfMonth.year)) {
            // Si no es bisiesto, no agregues la fecha 29
            continue
        }
        val date = LocalDate(
            firstDayOfMonth.year,
            firstDayOfMonth.monthNumber,
            day
        )
        dates.add(date to false)
    }

    // Devolver la lista de fechas
    return dates
}

fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
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
                            imageVector = Icons.Filled.KeyboardArrowLeft,
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
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )
                    },
                )
            Text(
                text = month.formatToMonthString(),
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

fun LocalDate.formatToMonthString(): String {
    val monthNames = listOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )
    return monthNames[this.month.ordinal].replaceFirstChar { it.uppercase() }
}