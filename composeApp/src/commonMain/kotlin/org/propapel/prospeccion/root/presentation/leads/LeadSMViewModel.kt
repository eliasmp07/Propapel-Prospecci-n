package org.propapel.prospeccion.root.presentation.leads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.presentation.ui.asUiText
import org.propapel.prospeccion.root.domain.repository.CustomerRepository
import org.propapel.prospeccion.root.domain.repository.ReminderRepository
import org.propapel.prospeccion.root.presentation.detailLead.DetailLeadAction

class LeadSMViewModel(
    private val customerRepository: CustomerRepository,
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private var _state = MutableStateFlow(LeadSMState())
    val state: StateFlow<LeadSMState> get() = _state.asStateFlow()

    init {
        onRefresh()
    }

    fun onAction(
        action: LeadAction
    ) {
        when (action) {
            LeadAction.OnRefresh -> onRefresh()
            is LeadAction.OnToggleCreateAppointmentDialog -> {
                _state.update {
                    it.copy(
                        leadId = action.leadId,
                        showCreateDate = !it.showCreateDate
                    )
                }
            }
            is LeadAction.OnNoteAppointmentChange -> {
                _state.update {
                    it.copy(
                        notesAppointment = action.notes
                    )
                }
            }
            is LeadAction.OnDateNextReminder -> {
                _state.update {
                    it.copy(
                        dateNextReminder = action.date
                    )
                }
            }
            LeadAction.CreateAppointmentClick -> {
                createAppointment()
            }
            else -> Unit
        }
    }

    private fun createAppointment(
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isCreatingAppointment = true
                )
            }
            val result = reminderRepository.createReminder(
                reminderDate = _state.value.dateNextReminder,
                customerId = _state.value.leadId,
                description = _state.value.notesAppointment
            )
            when(result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            isCreatingAppointment = false
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        it.copy(
                            leadId = 0,
                            showCreateDate = !it.showCreateDate,
                            isCreatingAppointment = false
                        )
                    }
                }
            }
        }
    }

    private fun onRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isRefreshing = true, error = null)
            }
            when (val result = customerRepository.getMyCustomers()) {
                is ResultExt.Error -> {
                    _state.update { it.copy(customers = listOf(), isRefreshing = false, error = result.error.asUiText()) }
                }

                is ResultExt.Success -> {

                    _state.update { it.copy(customers = result.data, isRefreshing = false, error = null) }
                }
            }
        }
    }
}