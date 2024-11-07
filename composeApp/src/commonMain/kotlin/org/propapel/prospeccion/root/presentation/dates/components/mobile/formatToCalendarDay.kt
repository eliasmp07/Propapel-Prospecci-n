package org.propapel.prospeccion.root.presentation.dates.components.mobile


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber

private fun LocalDate.formatToCalendarDay(): String = this.dayOfMonth.toString()

@Composable
fun CalendarCell(
    date: LocalDate,
    isBusy: Boolean,

    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val text = date.formatToCalendarDay()
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .padding(2.dp)
            .background(
                shape = RoundedCornerShape(CornerSize(8.dp)),
                color = Color.White,
            )
            .clip(RoundedCornerShape(CornerSize(8.dp)))
            .clickable(onClick = onClick)
    ) {
        if (isBusy) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .padding(8.dp)
                    .background(
                        shape = CircleShape,
                        color = Color.Red.copy(alpha = 0.5f)
                    )
            )
        }
        Text(
            text = text,
            color = colorScheme.onSecondaryContainer,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

fun Int.getDayOfWeek3Letters(): String {
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val dayIndex = (this % 7)
    return daysOfWeek.getOrNull(dayIndex) ?: ""
}

@Composable
fun WeekdayCell(weekday: Int, modifier: Modifier = Modifier) {
    val text = weekday.getDayOfWeek3Letters()
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
    ) {
        Text(
            text = text,
            color = colorScheme.onPrimaryContainer,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun CalendarGrid(
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
            CalendarCell(
                date = day.first,
                isBusy = isReminder,
                onClick = { onClick(day.first) }
            )
        }
    }
}

fun LocalDate.dayOfWeekIso(): Int {
    return this.dayOfWeek.isoDayNumber
}

fun getWeekDays(startFromSunday: Boolean): List<Int> {
    val lista = (1..7).toList()
    return if (startFromSunday) lista.dropLast(1) + lista.last() else lista
}

@Composable
fun CalendarCustomLayout(
    modifier: Modifier = Modifier,
    horizontalGapDp: Dp = 2.dp,
    verticalGapDp: Dp = 2.dp,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val horizontalGap = with(density) { horizontalGapDp.roundToPx() }
    val verticalGap = with(density) { verticalGapDp.roundToPx() }

    Layout(
        content = content,
        modifier = modifier,
    ) { measurables, constraints ->
        val totalWidthWithoutGap = constraints.maxWidth - (horizontalGap * 6)
        val singleWidth = totalWidthWithoutGap / 7

        val xPos = mutableListOf<Int>()
        val yPos = mutableListOf<Int>()
        var currentX = 0
        var currentY = 0

        measurables.forEach { _ ->
            xPos.add(currentX)
            yPos.add(currentY)

            if (currentX + singleWidth + horizontalGap > totalWidthWithoutGap) {
                currentX = 0
                currentY += singleWidth + verticalGap
            } else {
                currentX += singleWidth + horizontalGap
            }
        }

        val placeables = measurables.map { measurable ->
            measurable.measure(
                constraints.copy(
                    maxWidth = singleWidth,
                    maxHeight = singleWidth
                )
            )
        }

        layout(
            width = constraints.maxWidth,
            height = currentY + singleWidth + verticalGap,
        ) {
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(
                    x = xPos[index],
                    y = yPos[index]
                )
            }
        }
    }
}
