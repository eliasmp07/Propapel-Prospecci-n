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
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.presentation.ui.asUiText
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.repository.CustomerRepository
import org.propapel.prospeccion.root.domain.repository.ReminderRepository

class CreateReminderViewModel(
    private val reminderRepository: ReminderRepository,
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private var _state = MutableStateFlow(CreateReminderState())
    val state: StateFlow<CreateReminderState> get() = _state.asStateFlow()

    init {
        getAllReminders()
    }

    fun onAction(
        action: CreateReminderAction
    ) {
        when (action) {
            CreateReminderAction.CreateAppointmentClick -> {
               createReminder()
            }
            is CreateReminderAction.OnTimeNextReminder -> {
                _state.update {
                    it.copy(
                        time = action.time
                    )
                }
            }
            is CreateReminderAction.OnTypeAppointmentChange -> {
                _state.update {
                    it.copy(
                        typeAppointment = action.type
                    )
                }
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
            CreateReminderAction.OnBackClick -> {
                if (_state.value.isSuccessCreate) {
                    _state.update {
                        it.copy(
                            isSuccessCreate = false
                        )
                    }
                }
            }
            CreateReminderAction.OnToggleDateNoAvailable -> {
                _state.update {
                    it.copy(
                        dateNoAvailable = !it.dateNoAvailable
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

    fun getAllReminders() {
        viewModelScope.launch(Dispatchers.IO){
            val result = reminderRepository.getAllMyReminders()
            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            error = result.error.asUiText(),
                            reminders = listOf()
                        )
                    }
                }
                is ResultExt.Success -> {

                    val reminderDateForm = result.data.map {
                        convertLocalDate(it.reminderDate.toLong())
                    }
                    _state.update {
                        it.copy(
                            reminders = reminderDateForm
                        )
                    }
                }
            }
        }
    }

    fun getAllMyCustomers() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val result = customerRepository.getMyCustomers()
            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            error = result.error.asUiText(),
                            isLoading = false
                        )
                    }
                }
                is ResultExt.Success -> {
                    val newList = result.data.toMutableList()
                    newList.sortBy {
                        it.companyName.uppercase()
                    }
                    _state.update {
                        it.copy(
                            customer = if (newList.isEmpty()) Customer() else newList[0],
                            customers = newList,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun createReminder() {
        viewModelScope.launch(Dispatchers.IO) {
            val dateReminder = _state.value.time + _state.value.date
            if (validAvailableDate(convertLocalDate(date = dateReminder))) {
                _state.update {
                    it.copy(
                        dateNoAvailable = !it.dateNoAvailable
                    )
                }
            }else{
                _state.update {
                    it.copy(
                        isCreatingAppointment = true,
                        error = null
                    )
                }
                val result = reminderRepository.createReminder(
                    reminderDate = dateReminder,
                    customerId = _state.value.customer.idCustomer,
                    description = _state.value.notesAppointment,
                    typeAppointment = _state.value.typeAppointment
                )
                when (result) {
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
                                isSuccessCreate = true,
                                isCreatingAppointment = false,
                                error = null,
                            )
                        }
                    }
                }
            }

        }
    }


    private fun validAvailableDate(date: LocalDateTime): Boolean {
        val margenMinimoMillis = 3_600_000 // 1 hora en milisegundos

        return _state.value.reminders.any { reminder ->
            // Calcula la diferencia en milisegundos entre el recordatorio y la fecha dada
            val diferenciaMillis = kotlin.math.abs(
                reminder.toInstant(TimeZone.UTC).toEpochMilliseconds() - date.toInstant(TimeZone.UTC).toEpochMilliseconds()
            )

            // Valida si la diferencia es menor que el margen mínimo en milisegundos
            diferenciaMillis < margenMinimoMillis
        }
    }


}

fun convertLocalDate(date: Long): LocalDateTime {
    return Instant.fromEpochMilliseconds(date).toLocalDateTime(TimeZone.UTC)
}
