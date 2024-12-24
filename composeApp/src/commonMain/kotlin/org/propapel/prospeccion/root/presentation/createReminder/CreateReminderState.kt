package org.propapel.prospeccion.root.presentation.createReminder

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.presentation.ui.UiText
import org.propapel.prospeccion.root.domain.models.Customer

data class CreateReminderState(
    val customer: Customer = Customer(),
    val customers: List<Customer> = listOf(Customer()),
    val reminders: List<LocalDateTime> = listOf(),
    val typeAppointment: String = "",
    val dateNoAvailable: Boolean = false,
    val notesAppointment: String = "",
    val error: UiText? = null,
    val isLoading: Boolean = false,
    val leadId: Int = 0,
    val isCreatingAppointment: Boolean = false,
    val time: Long = 0L,
    val date: Long = 0L,
    val dateNextReminder: Long = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
    val isSuccessCreate: Boolean = false
)
