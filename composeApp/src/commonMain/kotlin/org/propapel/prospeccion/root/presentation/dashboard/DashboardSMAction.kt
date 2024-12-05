package org.propapel.prospeccion.root.presentation.dashboard

import kotlinx.datetime.LocalDate

sealed interface DashboardSMAction {
    data class OnWebViewClick(val url: String): DashboardSMAction
    data object OnRetryCustomer: DashboardSMAction
    data object OnRetryReminders: DashboardSMAction
    data class OnDateChange(val date: LocalDate): DashboardSMAction
    data object OnRefresh: DashboardSMAction
    data object OnCreateReminderClick: DashboardSMAction
    data object OnSearchLead: DashboardSMAction
    data class OnDetailReminderCustomer(val idReminder: String): DashboardSMAction
    data object OnMoveLeadScreenClick: DashboardSMAction
}