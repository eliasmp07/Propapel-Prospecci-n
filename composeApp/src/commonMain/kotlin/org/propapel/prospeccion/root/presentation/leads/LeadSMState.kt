package org.propapel.prospeccion.root.presentation.leads

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import org.propapel.prospeccion.core.presentation.ui.UiText
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Project
import org.propapel.prospeccion.root.domain.models.Purchase

data class LeadSMState(
    val typeAppointment: String = "",
    val dateNoAvailable: Boolean = false,
    val reminders: List<LocalDateTime> = listOf(),
    val customers: State<List<Customer>> = State.Loading(),
    val isRefreshing: Boolean = false,
    val productInteres: State<List<Purchase>> = State.Loading(),
    val error: UiText? = null,
    val project: State<List<Project>> = State.Loading(),
    val showCreateDate: Boolean = false,
    //Create cita
    val notesAppointment: String = "",
    val leadId: Int = 0,
    val isCreatingAppointment: Boolean = false,
    val dateNextReminder: Long = Clock.System.now().toEpochMilliseconds(),
)
