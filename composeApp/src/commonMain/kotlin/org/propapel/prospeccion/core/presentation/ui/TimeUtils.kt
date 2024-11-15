package org.propapel.prospeccion.core.presentation.ui

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun typeHour(hora: Int): String{
    return if (hora >= 13) {
        "PM"
    } else {
        "AM"
    }
}

object TimeUtils {
    val DateNow = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
    val hourNow = Clock.System.now().toLocalDateTime(TimeZone.UTC).hour
    val minuteNow = Clock.System.now().toLocalDateTime(TimeZone.UTC).minute
    val secondNow = Clock.System.now().toLocalDateTime(TimeZone.UTC).second
    val dayNow = Clock.System.now().toLocalDateTime(TimeZone.UTC).dayOfMonth
    val monthNow = Clock.System.now().toLocalDateTime(TimeZone.UTC).monthNumber
    val yearNow = Clock.System.now().toLocalDateTime(TimeZone.UTC).year
    val dayOfWeekNow = Clock.System.now().toLocalDateTime(TimeZone.UTC).dayOfWeek
    val dayOfYearNow = Clock.System.now().toLocalDateTime(TimeZone.UTC).dayOfYear
}

