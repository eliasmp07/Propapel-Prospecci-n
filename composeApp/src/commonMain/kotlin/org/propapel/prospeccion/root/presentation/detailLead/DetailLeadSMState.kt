package org.propapel.prospeccion.root.presentation.detailLead

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.presentation.designsystem.SuccessGreen
import org.propapel.prospeccion.core.presentation.ui.UiText
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Project
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.domain.models.Reminder

@Stable
data class DetailLeadSMState(
    val price: String = "",
    val reminderClose: Reminder = Reminder(),
    val productsInterest: List<PurchaseRequest> = listOf(),
    val productInterest: String = "Seleccione una opción",
    val date: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val congratulationsCloseProject: Boolean = false,
    val errorColor: Color = SuccessGreen,
    val isLoading: Boolean = false,
    val isDeletingProject: Boolean = false,
    val typeAppointment: String = "",
    val competencia: String = "",
    val motivos: String = "",
    val comments: String = "",
    val projectDelete: Project = Project(),
    val successDelete: Boolean = false,
    val customer: Customer = Customer(),
    val reminders: List<Reminder> = emptyList(),
    val project: List<Project> = listOf(),
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
