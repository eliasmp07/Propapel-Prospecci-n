package org.propapel.prospeccion.root.presentation.detailLead

import org.propapel.prospeccion.root.domain.models.Project
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.domain.models.Reminder

sealed interface DetailLeadAction {
    data class OnProductInterestChange(val product: String): DetailLeadAction
    data class OnRemoveProductInterestClick(val product: PurchaseRequest): DetailLeadAction
    data object OnAddProductClick: DetailLeadAction

    data class OnPriceChange(val price: String): DetailLeadAction

    data object OnCloseAppointment: DetailLeadAction

    data class OnTypeAppointmentChange(val type: String): DetailLeadAction

    data class OnCloseReminder(val reminder: Reminder): DetailLeadAction

    data object OnCloseProject: DetailLeadAction

    data class OnCommentsChange(val comments: String): DetailLeadAction
    data class OnMotivosChange(val motivos: String): DetailLeadAction
    data class OnCompetenciaChange(val competencia: String): DetailLeadAction

    data class OnDeleteProject(val project: Project): DetailLeadAction

    data object OnConfirmProject: DetailLeadAction

    data class OnUpdateReminder(val reminder: Reminder): DetailLeadAction

    data object OnDimissUpdateReminder: DetailLeadAction

    data object HideError: DetailLeadAction
    data object OnBackClick : DetailLeadAction
    data object OnToggleCreateAppointmentDialog: DetailLeadAction

    data object OnToggleDeleteReminderConfirm: DetailLeadAction

    data class OnCancelReminderClick(val reminder: Reminder): DetailLeadAction

    data object OnConfirmCancelarReminder: DetailLeadAction

    data class OnUpdateCustomerClick(val idCustomer: String): DetailLeadAction


    data class OnDetailReminderCustomer(val idReminder: String): DetailLeadAction

    data class AddInteractionsClick(val idCustomer: String): DetailLeadAction

    data class OnNoteAppointmentChange(val notes: String): DetailLeadAction
    data object CreateAppointmentClick: DetailLeadAction

    data object UpdateReminderClick: DetailLeadAction
    data class OnDateNextReminder(val date: Long): DetailLeadAction

    data class OnCreateProject(val customerId: String): DetailLeadAction
}