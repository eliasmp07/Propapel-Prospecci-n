package org.propapel.prospeccion.root.presentation.dates

import kotlinx.datetime.LocalDate
import org.propapel.prospeccion.root.domain.models.Reminder

data class DatesSMState(
    val reminders: List<Reminder> = listOf(),
    val datesReminders: List<LocalDate> = listOf(),
    val isSelectedDate: Boolean = false
)
