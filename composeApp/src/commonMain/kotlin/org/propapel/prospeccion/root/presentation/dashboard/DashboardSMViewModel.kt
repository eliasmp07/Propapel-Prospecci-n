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
import org.propapel.prospeccion.core.presentation.ui.asUiText
import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.domain.repository.CustomerRepository
import org.propapel.prospeccion.root.domain.repository.InteractionRepository
import org.propapel.prospeccion.root.domain.repository.ReminderRepository

class DashboardSMViewModel(
    private val reminderRepository: ReminderRepository,
    private val customerRepository: CustomerRepository,
    private val interactionRepository: InteractionRepository
) : ViewModel() {

    private var _state = MutableStateFlow(DashboardSMState())
    val state: StateFlow<DashboardSMState> get() = _state.asStateFlow()

    init {
        onInitialLoad()
    }

    // Método de carga inicial
    private fun onInitialLoad() {
        _state.update { it.copy(isLoading = true) }
        getAllMyReminders()
        getMyInteractions()
        getAllCustomers()
        getMyCustomer()
    }

    private fun getMyCustomer(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getMyCustomers()
            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            myCustomer = emptyList(),
                            error = result.error.asUiText()
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update { it.copy(myCustomer = result.data) }
                }
            }
        }
    }

    // Método de refresco (separa el loading del refreshing)
    private fun onRefresh() {
        _state.update { it.copy(isRefreshing = true, error = null) }
        getAllMyReminders()
        getMyInteractions()
        getAllCustomers()
    }

    private fun getAllCustomers() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getAllCustomers()
            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            customers = emptyList(),
                            error = result.error.asUiText()
                        )
                    }
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

    private fun getMyInteractions() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getMyCustomers()
            when (result) {
                is ResultExt.Error -> {
                    _state.update { it.copy(reminders = listOf()) }
                }
                is ResultExt.Success -> {
                    val currentMoment = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    val currentMonth = currentMoment.month
                    val currentYear = currentMoment.year

                    val remindersThisMonth = result.data.flatMap {
                        it.interactions
                    }

                    val interactions = remindersThisMonth.filter {interaction ->
                        val reminderDate =
                            Instant.fromEpochMilliseconds(interaction.interactionDate).toLocalDateTime(
                                TimeZone.currentSystemDefault()
                            )
                        reminderDate.month == currentMonth && reminderDate.year == currentYear && interaction.interactionType == InteractionType.PRESENCIAL.name
                    }

                    _state.update {
                        it.copy(
                            totalRemindersMoth = interactions.size.toDouble()
                        )
                    }
                }
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
                    _state.update {
                        it.copy(
                            reminders = result.data,
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: DashboardSMAction) {
        when (action) {
            DashboardSMAction.OnRetryClick -> {
                onInitialLoad() // Llama el método de carga inicial
            }
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
