package org.propapel.prospeccion.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus

@Composable
fun HomeDateSelector(
    selectedDate: LocalDate,
    mainDate: LocalDate,
    onDateClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    datesToShow: Int = 7 // Número de días a mostrar: de domingo a sábado
) {
    // Determinamos qué día de la semana es la fecha actual (mainDate)
    val currentDayOfWeek = mainDate.dayOfWeek.isoDayNumber // Lunes = 1, Domingo = 7

    // Ajustamos el inicio de la semana al domingo. Si hoy es domingo, no restamos días
    val daysToSunday = if (currentDayOfWeek == 7) 0 else currentDayOfWeek // Domingo = 7
    val startOfWeek = mainDate.minus(DatePeriod(days = daysToSunday))

    // Creamos una fila con los días desde domingo hasta sábado
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        for (i in 0 until datesToShow) {
            val date = startOfWeek.plus(DatePeriod(days = i))
            HomeDateItem(
                modifier = Modifier.weight(1f).pointerHoverIcon(PointerIcon.Hand),
                date = date,
                isSelected = selectedDate == date
            ) {
                onDateClick(date)
            }
        }
    }
}