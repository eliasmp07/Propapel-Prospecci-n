package org.propapel.prospeccion.root.presentation.dates

import org.propapel.prospeccion.root.domain.models.Reminder

data class DatesSMState(
    val reminders: List<Reminder> = listOf()
)
