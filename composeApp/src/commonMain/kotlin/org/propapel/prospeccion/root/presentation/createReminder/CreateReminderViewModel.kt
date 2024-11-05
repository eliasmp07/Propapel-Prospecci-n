package org.propapel.prospeccion.root.presentation.createReminder

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
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.repository.CustomerRepository
import org.propapel.prospeccion.root.domain.repository.ReminderRepository

class CreateReminderViewModel(
    private val reminderRepository: ReminderRepository,
    private val customerRepository: CustomerRepository
): ViewModel() {

    private var _state = MutableStateFlow(CreateReminderState())
    val state: StateFlow<CreateReminderState> get() = _state.asStateFlow()

    fun onAction(
        action: CreateReminderAction
    ){
        when(action){
            CreateReminderAction.CreateAppointmentClick -> {
                createReminder()
            }
            is CreateReminderAction.OnCustomerChange -> {
                _state.update {
                    it.copy(
                        customer = action.customer
                    )
                }
            }
            is CreateReminderAction.OnDateNextReminder -> {
                _state.update {
                    it.copy(
                        dateNextReminder = action.date
                    )
                }
            }
            is CreateReminderAction.OnNoteAppointmentChange -> {
                _state.update {
                    it.copy(
                        notesAppointment = action.notes
                    )
                }
            }
            else -> Unit
        }
    }

    fun getAllMyCustomers(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val result = customerRepository.getMyCustomers()
            when(result){
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        it.copy(
                            customer = result.data.first(),
                            customers = result.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun createReminder(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isCreatingAppointment = true,
                    error = null
                )
            }
            val result = reminderRepository.createReminder(
                reminderDate = _state.value.dateNextReminder,
                customerId = _state.value.customer.idCustomer,
                description = _state.value.notesAppointment
            )
            when(result){
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            error = result.error.asUiText()
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        it.copy(
                            isCreatingAppointment = false,
                            error = null,
                        )
                    }
                }
            }
        }
    }
}