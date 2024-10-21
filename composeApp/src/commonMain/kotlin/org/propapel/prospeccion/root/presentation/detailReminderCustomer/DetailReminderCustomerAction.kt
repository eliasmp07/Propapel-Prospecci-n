package org.propapel.prospeccion.root.presentation.detailReminderCustomer

sealed interface DetailReminderCustomerAction {
    data object OnBackClick: DetailReminderCustomerAction
}