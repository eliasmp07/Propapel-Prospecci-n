package org.propapel.prospeccion.root.presentation.detailReminderCustomer

sealed interface DetailReminderCustomerAction {
    data object OnBackClick: DetailReminderCustomerAction
    data class OnCompleteReminderClick(val reminderId: String) : DetailReminderCustomerAction
}