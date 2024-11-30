package org.propapel.prospeccion.core.presentation.ui.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

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