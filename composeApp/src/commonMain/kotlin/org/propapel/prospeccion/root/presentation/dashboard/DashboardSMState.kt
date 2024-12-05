package org.propapel.prospeccion.root.presentation.dashboard

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Reminder
import org.propapel.prospeccion.root.presentation.dashboard.components.mobile.Banner
import org.propapel.prospeccion.root.presentation.leads.UiState

data class DashboardSMState(
    val banners: UiState<List<Banner>> = UiState.Loading(),
    val myCustomer: UiState<List<Customer>> = UiState.Loading(),
    val reminderDate: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val date: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val reminders: UiState<List<Reminder>> = UiState.Loading(),
    val totalRemindersMoth: Double = 0.0,
    val isRefreshing: Boolean = false
)
