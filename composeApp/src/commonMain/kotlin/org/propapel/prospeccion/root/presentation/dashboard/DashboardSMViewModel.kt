package org.propapel.prospeccion.root.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.root.domain.repository.CustomerRepository
import org.propapel.prospeccion.root.domain.repository.ReminderRepository

class DashboardSMViewModel(
    private val reminderRepository: ReminderRepository,
    private val customerRepository: CustomerRepository
): ViewModel() {

    private var _state = MutableStateFlow(DashboardSMState())
    val state: StateFlow<DashboardSMState> get() = _state.asStateFlow()

    init {
        onInitialLoad()
    }

    // Método de carga inicial
    private fun onInitialLoad() {
        _state.update { it.copy(isLoading = true) }
        getAllMyReminders()
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getMyCustomers()
            when (result) {
                is ResultExt.Error -> {
                    _state.update { it.copy(myCustomer = emptyList()) }
                }
                is ResultExt.Success -> {
                    _state.update { it.copy(myCustomer = result.data) }
                }
            }
            getAllCustomers()
        }
    }

    // Método de refresco (separa el loading del refreshing)
    private fun onRefresh() {
        _state.update { it.copy(isRefreshing = true) }
        getAllMyReminders()
        getAllCustomers()
    }

    private fun getAllCustomers() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getAllCustomers()
            when (result) {
                is ResultExt.Error -> {
                    _state.update { it.copy(customers = emptyList()) }
                }
                is ResultExt.Success -> {
                    _state.update { it.copy(customers = result.data) }
                }
            }
            _state.update {
                it.copy(
                    isLoading = false, // Solo desactiva el loading si es la carga inicial
                    isRefreshing = false // Desactiva refreshing si viene de un refresh
                )
            }
        }
    }

    private fun getAllMyReminders() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = reminderRepository.getAllMyReminders()
            when (result) {
                is ResultExt.Error -> {
                    _state.update { it.copy(reminders = listOf()) }
                }
                is ResultExt.Success -> {
                    val currentMoment = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    val currentMonth = currentMoment.month
                    val currentYear = currentMoment.year

                    val remindersThisMonth = result.data.filter { reminder ->
                        val reminderDate = Instant.fromEpochMilliseconds(reminder.reminderDate.toLong()).toLocalDateTime(
                            TimeZone.currentSystemDefault())
                        reminderDate.month == currentMonth && reminderDate.year == currentYear
                    }

                    _state.update { it.copy(reminders = result.data, totalRemindersMoth = remindersThisMonth.size.toDouble()) }
                }
            }
        }
    }

    fun onAction(action: DashboardSMAction) {
        when (action) {
            is DashboardSMAction.OnDateChange -> {
                _state.update { it.copy(date = action.date) }
            }

            DashboardSMAction.OnRefresh -> {
                onRefresh() // Llama el método de refresco separado
            }
            else -> Unit
        }
    }
}
