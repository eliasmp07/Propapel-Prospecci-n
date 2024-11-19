package org.propapel.prospeccion.root.presentation.detailLead

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.presentation.ui.UiText
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Reminder
import org.propapel.prospeccion.root.domain.repository.CustomerRepository
import org.propapel.prospeccion.root.domain.repository.ProjectRepository
import org.propapel.prospeccion.root.domain.repository.ReminderRepository

class DetailLeadViewModel(
    private val customerRepository: CustomerRepository,
    private val reminderRepository: ReminderRepository,
    private val projectRepository: ProjectRepository
) : ViewModel() {

    private var _state = MutableStateFlow(DetailLeadSMState())
    val state: StateFlow<DetailLeadSMState> get() = _state.asStateFlow()

    fun onAction(action: DetailLeadAction) {
        when (action) {
            DetailLeadAction.OnToggleCreateAppointmentDialog -> {
                _state.update {
                    it.copy(
                        showCreateDate = !it.showCreateDate
                    )
                }
            }
            DetailLeadAction.HideError -> {
                _state.update {
                    it.copy(isError = !it.isError)
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
            DetailLeadAction.OnConfirmCancelarReminder -> {
                viewModelScope.launch(
                    Dispatchers.IO
                ) {
                    val result = reminderRepository.deleteReminder(_state.value.reminderEliminated)

                    when(result){
                        is ResultExt.Error -> {
                            _state.update {
                                it.copy(
                                    showCancelNotification = !it.showCancelNotification,
                                    error = UiText.DynamicString("Error al eliminar la cita"),
                                    isError = true,
                                )
                            }
                        }
                        is ResultExt.Success -> {
                            _state.update {
                                val reminders = it.customer.reminders.toMutableList()
                                reminders.remove(it.reminderEliminated)
                                it.copy(
                                    customer = it.customer.copy(
                                        reminders = reminders
                                    ),
                                    error = UiText.DynamicString("Cita eliminada con éxito"),
                                    isError = true,
                                    showCancelNotification = !it.showCancelNotification,
                                    reminderEliminated = Reminder()
                                )
                            }
                        }
                    }

                }
            }
            is DetailLeadAction.OnDeleteProject -> {
                _state.update {
                    it.copy(
                        projectDelete = action.project
                    )
                }
            }
            DetailLeadAction.OnConfirmProject ->{
                deleteProject()
            }
            DetailLeadAction.OnDimissUpdateReminder -> {
                _state.update {
                    it.copy(
                        showDialogUpdateReminder = !it.showDialogUpdateReminder
                    )
                }
            }
            is DetailLeadAction.OnMotivosChange -> {
                _state.update {
                    it.copy(
                        motivos = action.motivos
                    )
                }
            }
            is DetailLeadAction.OnCompetenciaChange -> {
                _state.update {
                    it.copy(
                        competencia = action.competencia
                    )
                }
            }
            is DetailLeadAction.OnUpdateReminder -> {
                _state.update {
                    it.copy(
                        showDialogUpdateReminder = !it.showDialogUpdateReminder,
                        reminderIdUpdate = action.reminder.reminderId,
                        notesAppointment = action.reminder.description,
                        dateNextReminder = action.reminder.reminderDate.toLong(),
                    )
                }
            }
            DetailLeadAction.UpdateReminderClick -> {
                updateReminder()
            }
            DetailLeadAction.OnToggleDeleteReminderConfirm -> {
                _state.update {
                    it.copy(
                        showCancelNotification = !it.showCancelNotification
                    )
                }
            }
            is DetailLeadAction.OnCancelReminderClick -> {
                _state.update {
                    it.copy(
                        reminderEliminated = action.reminder,
                        showCancelNotification = !it.showCancelNotification
                    )
                }
            }
            else -> Unit
        }
    }

    private fun updateReminder() {
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            _state.update {
                it.copy(
                    isUpdateReminder = true
                )
            }
            val result = reminderRepository.updateReminder(
                Reminder(
                    reminderId = _state.value.reminderIdUpdate,
                    reminderDate = _state.value.dateNextReminder.toString(),
                    customer = _state.value.customer,
                    description = _state.value.notesAppointment
                )
            )

            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            isUpdateReminder = false,
                            showDialogUpdateReminder = !it.showDialogUpdateReminder,
                            error = UiText.DynamicString("Error al actualizar la cita"),
                            isError = true
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        val reminders = it.customer.reminders.toMutableList()
                        val index = reminders.indexOfFirst { reminder -> reminder.reminderId == _state.value.reminderIdUpdate }
                        reminders[index] = Reminder(
                            reminderId = _state.value.reminderIdUpdate,
                            reminderDate = _state.value.dateNextReminder.toString(),
                            customer = _state.value.customer,
                            description = _state.value.notesAppointment
                        )
                        it.copy(
                            isUpdateReminder = false,
                            showDialogUpdateReminder = !it.showDialogUpdateReminder,
                            dateNextReminder = 0,
                            notesAppointment = "",
                            error = UiText.DynamicString("Cita actualizada con éxito"),
                            isError = true,
                            customer = it.customer.copy(
                                reminders = reminders
                            )
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
                customerId = _state.value.customer.idCustomer,
                description = _state.value.notesAppointment
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
                        val reminders = it.customer.reminders.toMutableList()
                        reminders.add(result.data)
                        it.copy(
                            showCreateDate = !it.showCreateDate,
                            dateNextReminder = 0,
                            notesAppointment = "",
                            error = UiText.DynamicString("Cita creada con éxito"),
                            isError = true,
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

    private fun deleteProject(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isDeletingProject = true
                )
            }
            val result = projectRepository.deleteProject(
                projectId = _state.value.projectDelete.id,
                motivos = _state.value.motivos,
                comments = _state.value.comments,
                competencia = _state.value.competencia
            )
            _state.update {
                it.copy(
                    isDeletingProject = false
                )
            }
            when(result){
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            successDelete = false
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update{
                        val projects = it.project.filter { it != _state.value.projectDelete }
                        it.copy(
                            project = projects,
                            error = UiText.DynamicString("Proyecto eliminado con éxito"),
                            isError = true,
                            successDelete = true
                        )
                    }
                }
            }
        }
    }

    fun getProjectByCustomerId(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val result = projectRepository.getProjectByCustomerId(id.toString())
            when(result){
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            project = emptyList()
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        val projectsFilter = result.data.filter {
                            !it.isCancel
                        }
                        it.copy(
                            project = projectsFilter
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
            getProjectByCustomerId(id)
            val result = customerRepository.getCustomerById(id.toString())
            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            customer = Customer()
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            customer = result.data
                        )
                    }
                }
            }
        }
    }

}