package org.propapel.prospeccion.root.presentation.leads

import kotlinx.datetime.Clock
import org.propapel.prospeccion.core.presentation.ui.UiText
import org.propapel.prospeccion.root.domain.models.Customer

data class LeadSMState(
    val customers: List<Customer> = listOf(),
    val isRefreshing: Boolean = false,
    val error: UiText? = null,

    val showCreateDate: Boolean = false,
    //Create cita
    val notesAppointment: String = "",
    val leadId: Int = 0,
    val isCreatingAppointment: Boolean = false,
    val dateNextReminder: Long = Clock.System.now().toEpochMilliseconds(),
)
