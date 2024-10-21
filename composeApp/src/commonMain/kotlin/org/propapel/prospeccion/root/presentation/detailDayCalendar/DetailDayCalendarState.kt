package org.propapel.prospeccion.root.presentation.detailDayCalendar

import androidx.compose.runtime.Stable
import org.propapel.prospeccion.root.domain.models.Reminder

@Stable
data class DetailDayCalendarState(
    val myReminders: List<Reminder> = listOf()
)
