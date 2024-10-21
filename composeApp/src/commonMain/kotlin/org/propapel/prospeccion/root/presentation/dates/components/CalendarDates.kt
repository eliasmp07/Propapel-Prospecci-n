package org.propapel.prospeccion.root.presentation.dates.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.propapel.prospeccion.core.presentation.designsystem.components.HomeDateItem

@Composable
fun CalendarDateSelector(
    selectedDate: LocalDate,
    mainDate: LocalDate,
    onDateClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    // Determinamos qué día de la semana es la fecha actual (mainDate)
    val currentDayOfWeek = mainDate.dayOfWeek.isoDayNumber // Lunes = 1, Domingo = 7

    // Ajustamos el inicio de la semana al domingo. Si hoy es domingo, no restamos días
    val daysToSunday = if (currentDayOfWeek == 7) 0 else currentDayOfWeek // Domingo = 7
    val startOfWeek = mainDate.minus(DatePeriod(days = daysToSunday))

    // Creamos una fila con los días desde domingo hasta sábado
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier.fillMaxWidth()
    ) {
        items(30){
            val date = startOfWeek.plus(DatePeriod(days = it))
            HomeDateItem(
                modifier = Modifier.padding(8.dp),
                date = date,
                isSelected = selectedDate == date
            ) {
                onDateClick(date)
            }
        }
    }
}