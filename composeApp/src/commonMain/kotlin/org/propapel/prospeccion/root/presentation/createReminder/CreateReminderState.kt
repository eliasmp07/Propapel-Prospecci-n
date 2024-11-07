package org.propapel.prospeccion.root.presentation.createReminder

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.propapel.prospeccion.core.presentation.ui.UiText
import org.propapel.prospeccion.root.domain.models.Customer

data class CreateReminderState(
    val customer: Customer = Customer(),
    val customers: List<Customer> = listOf(Customer()),
    val reminders: List<LocalDateTime> = listOf(),
    val showDatePicker: Boolean = false,
    val dateNoAvailable: Boolean = false,
    val notesAppointment: String = "",
    val error: UiText? = null,
    val isLoading: Boolean = false,
    val leadId: Int = 0,
    val isCreatingAppointment: Boolean = false,
    val dateNextReminder: Long = Clock.System.now().toEpochMilliseconds(),
    val isSuccessCreate: Boolean = false
)
