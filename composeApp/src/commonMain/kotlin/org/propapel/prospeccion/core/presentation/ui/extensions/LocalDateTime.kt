package org.propapel.prospeccion.core.presentation.ui.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.presentation.ui.utils.getDaysInMonth

fun LocalDateTime.previousYear(): Int{
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return  if (currentDate.monthNumber == 1) currentDate.year - 1 else currentDate.year
}

fun LocalDateTime.previousMoth(): Int{
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return  if (currentDate.monthNumber == 1) 12 else currentDate.monthNumber - 1
}

fun LocalDateTime.previewTwoMoth(): Int{
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    return  if (currentDate.monthNumber == 1) 12 else currentDate.monthNumber - 2
}

fun LocalDate.formatToMonthString(): String {
    val monthNames = listOf(
        "Enero",
        "Febrero",
        "Marzo",
        "Abril",
        "Mayo",
        "Junio",
        "Julio",
        "Agosto",
        "Septiembre",
        "Octubre",
        "Noviembre",
        "Diciembre"
    )
    return monthNames[this.month.ordinal].replaceFirstChar { it.uppercase() }
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

