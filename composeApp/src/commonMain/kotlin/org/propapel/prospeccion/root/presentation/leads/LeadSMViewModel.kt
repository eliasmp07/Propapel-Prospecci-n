package org.propapel.prospeccion.root.presentation.leads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.presentation.ui.UiText
import org.propapel.prospeccion.core.presentation.ui.asUiText
import org.propapel.prospeccion.root.domain.models.Project
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
        onRefresh()
        getProjects()
    }

    fun onAction(
        action: LeadAction
    ) {
        when (action) {
            LeadAction.OnRefresh -> {
                _state.update {
                    it.copy(
                        customers = State.Loading()
                    )
                }
                onRefresh()
            }
            LeadAction.OnRetryProject -> {
                _state.update {
                    it.copy(
                        project = State.Loading()
                    )
                }
                getProjects()
            }
            is LeadAction.OnTypeAppointmentChange ->{
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
            is LeadAction.OnDateNextReminder -> {
                if (validAvailableDate(convertLocalDate(action.date))){
                    _state.update {
                        it.copy(
                            dateNoAvailable = !it.dateNoAvailable
                        )
                    }
                }else{
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

    private fun createAppointment(
    ) {
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

    private fun getProjects() {
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val result = projectRepository.getProjectByUserId()

            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            project = State.Error(result.error.asUiText())
                        )
                    }
                }
                is ResultExt.Success -> {
                    if (result.data.isEmpty()) {
                        _state.update {
                            it.copy(
                                project = State.Empty()
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                project = State.Success(result.data)
                            )
                        }
                    }

                }
            }
        }
    }

    private fun onRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isRefreshing = true,
                    error = null
                )
            }
            when (val result = customerRepository.getMyCustomers()) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            productInteres = State.Error(result.error.asUiText()),
                            customers = State.Error(result.error.asUiText()),
                            isRefreshing = false,
                            error = result.error.asUiText()
                        )
                    }
                }

                is ResultExt.Success -> {
                    if (result.data.isEmpty()){
                        _state.update {
                            it.copy(
                                customers = State.Empty()
                            )
                        }
                    }else{
                        val newList = result.data.toMutableList()
                        newList.sortBy {
                            it.companyName.uppercase()
                        }
                        val purchases = newList.flatMap {
                            it.purchase
                        }
                        if (purchases.isEmpty()){
                            _state.update {
                                it.copy(
                                    productInteres = State.Empty()
                                )
                            }
                        }else{
                            _state.update {
                                it.copy(
                                    productInteres = State.Success(purchases)
                                )
                            }
                        }
                        _state.update {
                            it.copy(
                                customers = State.Success(newList),
                                isRefreshing = false,
                                error = null
                            )
                        }
                    }

                }
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