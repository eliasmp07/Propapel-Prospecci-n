package org.propapel.prospeccion.root.presentation.createReminder

import kotlinx.datetime.Clock
import org.propapel.prospeccion.core.presentation.ui.UiText
import org.propapel.prospeccion.root.domain.models.Customer

data class CreateReminderState(
    val customer: Customer = Customer(),
    val customers: List<Customer> = listOf(Customer()),
    val notesAppointment: String = "",
    val error: UiText? = null,
    val isLoading: Boolean = false,
    val leadId: Int = 0,
    val isCreatingAppointment: Boolean = false,
    val dateNextReminder: Long = Clock.System.now().toEpochMilliseconds(),
)
