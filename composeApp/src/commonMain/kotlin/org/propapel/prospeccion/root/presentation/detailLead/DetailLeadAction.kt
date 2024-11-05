package org.propapel.prospeccion.root.presentation.detailLead

sealed interface DetailLeadAction {
    data object OnBackClick : DetailLeadAction
    data object OnToggleCreateAppointmentDialog: DetailLeadAction


    data class OnDetailReminderCustomer(val idReminder: String): DetailLeadAction

    data class OnNoteAppointmentChange(val notes: String): DetailLeadAction
    data object CreateAppointmentClick: DetailLeadAction
    data class OnDateNextReminder(val date: Long): DetailLeadAction
}