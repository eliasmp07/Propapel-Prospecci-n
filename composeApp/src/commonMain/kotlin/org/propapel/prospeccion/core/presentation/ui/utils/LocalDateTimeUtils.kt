package org.propapel.prospeccion.core.presentation.ui.utils

import kotlinx.datetime.LocalDate
import network.chaintech.kmp_date_time_picker.utils.withDayOfMonth
import org.propapel.prospeccion.core.presentation.ui.extensions.minusMonths

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


fun getDaysInMonth(
    year: Int,
    month: Int
): Int {
    return when (month) {
        1 -> 31 // Enero
        2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28 // Febrero
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