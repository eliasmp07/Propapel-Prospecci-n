package org.propapel.prospeccion.root.presentation.detailLead

import androidx.compose.runtime.Stable
import kotlinx.datetime.Clock
import org.propapel.prospeccion.root.domain.models.Customer

@Stable
data class DetailLeadSMState(
    val isLoading: Boolean = false,
    val customer: Customer = Customer(),
    val showCreateDate: Boolean = false,

    //Create cita
    val notesAppointment: String = "",
    val isCreatingAppointment: Boolean = false,
    val dateNextReminder: Long = Clock.System.now().toEpochMilliseconds(),
)
