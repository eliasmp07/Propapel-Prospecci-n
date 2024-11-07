package org.propapel.prospeccion.root.presentation.createReminder

import org.propapel.prospeccion.root.domain.models.Customer


sealed interface CreateReminderAction {
    data class OnNoteAppointmentChange(val notes: String): CreateReminderAction
    data object CreateAppointmentClick: CreateReminderAction
    data class OnDateNextReminder(val date: Long): CreateReminderAction
    data class OnCustomerChange(val customer: Customer): CreateReminderAction
    data object OnBackClick: CreateReminderAction
    data object OnToggleDateNoAvailable: CreateReminderAction
    data object OnShowDatePicker: CreateReminderAction
}