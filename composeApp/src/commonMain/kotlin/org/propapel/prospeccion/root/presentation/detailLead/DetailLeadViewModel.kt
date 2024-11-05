package org.propapel.prospeccion.root.presentation.detailLead

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
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.repository.CustomerRepository
import org.propapel.prospeccion.root.domain.repository.ReminderRepository

class DetailLeadViewModel(
    private val customerRepository: CustomerRepository,
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private var _state = MutableStateFlow(DetailLeadSMState())
    val state: StateFlow<DetailLeadSMState> get() = _state.asStateFlow()

    fun onAction(action: DetailLeadAction){
        when(action){
            DetailLeadAction.OnToggleCreateAppointmentDialog -> {
                _state.update {
                    it.copy(
                        showCreateDate = !it.showCreateDate
                    )
                }
            }
            is DetailLeadAction.OnNoteAppointmentChange -> {
                _state.update {
                    it.copy(
                        notesAppointment = action.notes
                    )
                }
            }
            is DetailLeadAction.OnDateNextReminder -> {
                _state.update {
                    it.copy(
                        dateNextReminder = action.date
                    )
                }
            }
            DetailLeadAction.CreateAppointmentClick -> {
                createAppointment()
            }
            else -> Unit
        }
    }

    private fun createAppointment(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isCreatingAppointment = true
                )
            }
            val result = reminderRepository.createReminder(
                reminderDate = _state.value.dateNextReminder,
                customerId = _state.value.customer.idCustomer,
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
                        val reminders = it.customer.reminders.toMutableList()
                        reminders.add(result.data)
                        it.copy(
                            showCreateDate = !it.showCreateDate,
                            customer = it.customer.copy(
                                reminders = reminders
                            ),
                            isCreatingAppointment = false
                        )
                    }
                }
            }
        }
    }
    fun getCustomerById(id: Int) {
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
                            isLoading = false,
                            customer = Customer()
                        )
                    }
                }
                is ResultExt.Success -> {
                    val customer = result.data.filter {
                        it.idCustomer == id
                    }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            customer = customer.first()
                        )
                    }
                }
            }
        }
    }

}