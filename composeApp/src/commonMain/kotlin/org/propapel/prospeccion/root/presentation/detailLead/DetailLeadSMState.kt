package org.propapel.prospeccion.root.presentation.detailLead

import androidx.compose.runtime.Stable
import kotlinx.datetime.Clock
import org.propapel.prospeccion.core.presentation.ui.UiText
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Reminder

@Stable
data class DetailLeadSMState(
    val isLoading: Boolean = false,
    val customer: Customer = Customer(),
    val isError: Boolean = false,
    val error: UiText = UiText.DynamicString("Error"),
    val showCreateDate: Boolean = false,
    val showCancelNotification: Boolean = false,
    val reminderEliminated: Reminder = Reminder(),
    val showDialogUpdateReminder: Boolean = false,
    val isUpdateReminder: Boolean = false,
    val reminderIdUpdate: Int = 0,
    //Create cita
    val notesAppointment: String = "",
    val isCreatingAppointment: Boolean = false,
    val dateNextReminder: Long = Clock.System.now().toEpochMilliseconds(),
)
