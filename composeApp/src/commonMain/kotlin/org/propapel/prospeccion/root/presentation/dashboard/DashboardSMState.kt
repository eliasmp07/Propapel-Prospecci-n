package org.propapel.prospeccion.root.presentation.dashboard

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.presentation.ui.UiText
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Reminder
import org.propapel.prospeccion.root.presentation.leads.State

data class DashboardSMState(
    val customers: List<Customer> = listOf(),
    val myCustomer: State<List<Customer>> = State.Loading(),
    val reminderDate: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val date: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val reminders: List<Reminder> = listOf(),
    val totalRemindersMoth: Double = 0.0,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false
)
