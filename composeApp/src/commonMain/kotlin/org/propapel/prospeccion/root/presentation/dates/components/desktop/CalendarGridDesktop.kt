package org.propapel.prospeccion.root.presentation.dates.components.desktop

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import org.propapel.prospeccion.root.presentation.dates.components.mobile.CalendarCell
import org.propapel.prospeccion.root.presentation.dates.components.mobile.CalendarCustomLayout
import org.propapel.prospeccion.root.presentation.dates.components.mobile.WeekdayCell
import org.propapel.prospeccion.root.presentation.dates.components.mobile.dayOfWeekIso
import org.propapel.prospeccion.root.presentation.dates.components.mobile.getWeekDays


@Composable
fun CalendarGridDesktop(
    date: List<Pair<LocalDate, Boolean>>,
    onClick: (LocalDate) -> Unit,
    datesReminder: List<LocalDate>,
    startFromSunday: Boolean,
    modifier: Modifier = Modifier,
) {
    val weekdayFirstDay = date.first().first.dayOfWeekIso()

    val weekdays = getWeekDays(startFromSunday)
    CalendarCustomLayout(modifier = modifier) {
        weekdays.forEach {
            WeekdayCell(weekday = it)
        }

        // Añade espacios para alinear el primer día del mes con el día correcto de la semana
        repeat(if (!startFromSunday) weekdayFirstDay - 2 else weekdayFirstDay - 1) {
            Spacer(modifier = Modifier)
        }

        // Recorre cada fecha solo una vez y verifica si está en la lista de recordatorios
        date.forEach { day ->
            val isReminder = datesReminder.any { it == day.first } // Comprueba si está en datesReminder
            CalendarCellDesktop(
                date = day.first,
                isBusy = isReminder,
                onClick = { onClick(day.first) }
            )
        }
    }
}