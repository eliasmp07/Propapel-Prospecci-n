package org.propapel.prospeccion.root.presentation.dashboard

import kotlinx.datetime.LocalDate

sealed interface DashboardSMAction {
    data class OnDateChange(val date: LocalDate): DashboardSMAction
    data object OnRefresh: DashboardSMAction
    data class OnDetailReminderCustomer(val idReminder: String): DashboardSMAction
    data object OnMoveLeadScreenClick: DashboardSMAction
}