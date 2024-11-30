package org.propapel.prospeccion.root.presentation.leads

sealed interface LeadAction {
    data object OnCreateReminder: LeadAction
    data class OnTypeAppointmentChange(val type: String): LeadAction
    data object OnRetryProject: LeadAction
    data object OnRetryCustomer: LeadAction
    data object OnAddLead : LeadAction
    data object OnRefresh: LeadAction
    data object OnDismissDialogDayNoAvailable: LeadAction
    data object OnAddLeadClick: LeadAction
    data class OnDetailLeadClick(val leadId: String): LeadAction
    data class OnUpdateLeadClick(val leadId: String): LeadAction
    data object OnSearchCustomerClick : LeadAction
    data class OnToggleCreateAppointmentDialog(val leadId: Int): LeadAction
    data class OnNoteAppointmentChange(val notes: String): LeadAction
    data object CreateAppointmentClick: LeadAction
    data class OnDateNextReminder(val date: Long): LeadAction
}