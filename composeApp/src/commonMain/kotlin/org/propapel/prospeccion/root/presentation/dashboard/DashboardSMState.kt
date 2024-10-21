package org.propapel.prospeccion.root.presentation.dashboard

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Reminder

data class DashboardSMState(
    val customers: List<Customer> = listOf(),
    val myCustomer: List<Customer> = listOf(),
    val date: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val reminders: List<Reminder> = listOf(),
    val totalRemindersMoth: Double = 0.0,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)
