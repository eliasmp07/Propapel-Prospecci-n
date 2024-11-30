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
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.presentation.ui.toImageAndTextError
import org.propapel.prospeccion.root.domain.repository.CustomerRepository
import org.propapel.prospeccion.root.domain.repository.ProjectRepository
import org.propapel.prospeccion.root.domain.repository.ReminderRepository
import org.propapel.prospeccion.root.presentation.createReminder.convertLocalDate

class LeadSMViewModel(
    private val customerRepository: CustomerRepository,
    private val reminderRepository: ReminderRepository,
    private val projectRepository: ProjectRepository
) : ViewModel() {

    private var _state = MutableStateFlow(LeadSMState())
    val state: StateFlow<LeadSMState> get() = _state.asStateFlow()


    init {
        getAllReminders()
        getMyCustomer()
        getProjects()
    }


    fun onAction(
        action: LeadAction
    ) {
        when (action) {
            LeadAction.OnRefresh -> {
                onRefresh()
            }
            LeadAction.OnRetryProject -> {
                _state.update {
                    it.copy(
                        project = UiState.Loading()
                    )
                }
                getProjects()
            }
            is LeadAction.OnTypeAppointmentChange -> {
                _state.update {
                    it.copy(
                        typeAppointment = action.type
                    )
                }
            }
            is LeadAction.OnToggleCreateAppointmentDialog -> {
                _state.update {
                    it.copy(
                        leadId = action.leadId,
                        showCreateDate = !it.showCreateDate
                    )
                }
            }
            LeadAction.OnDismissDialogDayNoAvailable -> {
                _state.update {
                    it.copy(
                        dateNoAvailable = !it.dateNoAvailable
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
            is LeadAction.OnRetryCustomer -> {
                getMyCustomer()
            }
            is LeadAction.OnDateNextReminder -> {
                if (validAvailableDate(convertLocalDate(action.date))) {
                    _state.update {
                        it.copy(
                            dateNoAvailable = !it.dateNoAvailable
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            dateNextReminder = action.date
                        )
                    }
                }

            }
            LeadAction.CreateAppointmentClick -> {
                createAppointment()
            }
            else -> Unit
        }
    }

    /**
     * Funcion que unicamente llama a lo clientes del usuario
     */
    private fun getMyCustomer() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    customers = UiState.Loading(),
                    productInteres = UiState.Loading()
                )
            }
            val result = customerRepository.getMyCustomers()
            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            productInteres = UiState.Error(result.error.toImageAndTextError()),
                            customers = UiState.Error(result.error.toImageAndTextError()),
                            isRefreshing = false,
                        )
                    }
                }
                is ResultExt.Success -> {
                    val newList = result.data.toMutableList()
                    newList.sortBy {
                        it.companyName.uppercase()
                    }
                    val purchases = newList.flatMap {
                        it.purchase
                    }
                    _state.update {
                        it.copy(
                            productInteres = purchases.toState(),
                            customers = newList.toState(),

                        )
                    }
                }
            }
        }
    }

    private fun createAppointment() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isCreatingAppointment = true
                )
            }
            val result = reminderRepository.createReminder(
                reminderDate = _state.value.dateNextReminder,
                customerId = _state.value.leadId,
                description = _state.value.notesAppointment,
                typeAppointment = _state.value.typeAppointment
            )
            when (result) {
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

    /**
     * Funcion que hace una llamada a los proyectos y devuelve una respuesta
     */
    private fun getProjects() {
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            _state.update {
                it.copy(
                    project = UiState.Loading()
                )
            }
            val result = projectRepository.getProjectByUserId()

            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            project = UiState.Error(result.error.toImageAndTextError())
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        it.copy(
                            project = result.data.toState()
                        )
                    }

                }
            }
        }
    }

    /**
     * Funcion que hace un llamado al refresh cuando alguna info no carga bien
     * es unicamente para el onRefresh
     */
    private fun onRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isRefreshing = true,
                )
            }
            getProjects()
            getAllReminders()
            getMyCustomer()
            _state.update {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }

    private fun getAllReminders() {
        viewModelScope.launch(Dispatchers.IO){
            val result = reminderRepository.getAllMyReminders()
            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
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