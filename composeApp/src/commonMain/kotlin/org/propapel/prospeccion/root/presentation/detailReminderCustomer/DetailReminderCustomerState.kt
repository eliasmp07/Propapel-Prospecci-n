package org.propapel.prospeccion.root.presentation.detailReminderCustomer

import androidx.compose.runtime.Stable
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.root.domain.models.Reminder


@Stable
data class DetailReminderCustomerState(
    val isLoading: Boolean = false,
    val isTodayAppointment: Boolean = false,
    val remindersDay: Reminder = Reminder(),
    val date: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
)
