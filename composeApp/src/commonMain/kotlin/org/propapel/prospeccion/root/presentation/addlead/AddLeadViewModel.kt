package org.propapel.prospeccion.root.presentation.addlead

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.domain.repository.CustomerRepository
import org.propapel.prospeccion.root.domain.repository.ReminderRepository
import org.propapel.prospeccion.root.presentation.createReminder.convertLocalDate

class AddLeadViewModel(
    private val customerRepository: CustomerRepository,
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private var _state = MutableStateFlow(AddLeadState())
    val state: StateFlow<AddLeadState> get() = _state.asStateFlow()

    init {
        getAllCustomers()
        getAllMyReminders()
        viewModelScope.launch {
            _state.map { it.nameCompany }
                .distinctUntilChanged()
                .collectLatest { name ->
                    val trimmedName = name?.trim().orEmpty() // Asegura que el nombre no sea nulo y elimina espacios
                    if (trimmedName.isEmpty()) {
                        // Si el campo está vacío, limpia el mensaje de error
                        _state.update {
                            it.copy(errorRazonSocial = null)
                        }
                    } else {
                        // Verifica similitud con nombres existentes
                        val isSimilarNameUnavailable = _state.value.listAllCustomers.any {
                            val existingName = it.companyName.trim()
                            existingName.contains(trimmedName, ignoreCase = true) ||
                                    trimmedName.contains(existingName, ignoreCase = true)
                        }
                        // Actualiza el estado con el error correspondiente
                        _state.update {
                            it.copy(
                                errorRazonSocial = if (isSimilarNameUnavailable) {
                                    "Hay un cliente con un nombre similar"
                                } else {
                                    null
                                }
                            )
                        }
                    }
                }
        }

    }

    fun onAction(
        action: AddLeadAction
    ) {
        when (action) {
            is AddLeadAction.OnAddressFiscalChange -> {
                _state.update { it.copy(fiscalAddress = action.fiscalAddress) }
            }

            is AddLeadAction.OnEmailChange -> {
                _state.update { it.copy(email = action.email) }
            }

            is AddLeadAction.OnFullNameChange -> {
                _state.update { it.copy(contactName = action.fullName) }
            }

            is AddLeadAction.OnPhoneNumberChange -> {
                _state.update { it.copy(phoneNumber = action.phoneNumber) }
            }

            is AddLeadAction.OnRazonSocialChange -> {
                _state.update { it.copy(nameCompany = action.razonSocial) }
            }
            is AddLeadAction.OnResponseOportunityChange -> {
                _state.update { it.copy(isOportunity = action.response) }
            }
            is AddLeadAction.OnTimeModifierChange -> {
                _state.update { it.copy(timeInteraction = action.time) }
            }
            is AddLeadAction.OnDateModifierChange -> {
                _state.update { it.copy(dateInteraction = action.date) }
            }
            AddLeadAction.OnSaveTaskClick -> {
                _state.update {
                    val tasks = it.tasks.toMutableList()
                    tasks.add(it.task)
                    it.copy(
                        tasks = tasks,
                        task = ""
                    )
                }
            }

            is AddLeadAction.OnTaskChange -> {
                _state.update {
                    it.copy(
                        task = action.task
                    )
                }
            }

            is AddLeadAction.OnRemoveTaskClick -> {
                _state.update {
                    val tasks = it.tasks.toMutableList()
                    tasks.remove(action.task)
                    it.copy(
                        tasks = tasks
                    )
                }
            }

            AddLeadAction.OnSelectIsOtherDateClick -> {
                _state.update { it.copy(isOtherDate = !it.isOtherDate) }
            }

            is AddLeadAction.OnTypeClientChange -> {
                _state.update {
                    it.copy(
                        typeClient = action.typeClient
                    )
                }
            }

            is AddLeadAction.OnTypeDateChange -> {
                _state.update {
                    it.copy(
                        typeDate = action.typeInteraction
                    )
                }
            }


            is AddLeadAction.OnProductInterestChange -> {
                _state.update {
                    it.copy(productInterest = action.product)
                }
            }
            is AddLeadAction.OnDescriptionNextReminderChange -> {
                _state.update {
                    it.copy(
                        descriptionNextAppointment = action.description
                    )
                }
            }
            is AddLeadAction.OnDateNextReminder -> {
                _state.update {
                    it.copy(
                        date = action.date
                    )
                }

            }
            is AddLeadAction.OnTypeAppointmentChange -> {
                _state.update {
                    it.copy(
                        typeAppointment = action.typeAppointment
                    )
                }
            }
            is AddLeadAction.OnRemoveProductInterestClick -> {
                _state.update {
                    val products = it.productsInterest.toMutableList()
                    products.remove(action.product)
                    it.copy(
                        productsInterest = products
                    )
                }
            }
            is AddLeadAction.OnPriceChange -> {
                _state.update {
                    it.copy(
                        price = action.price
                    )
                }
            }
            AddLeadAction.OnAddProductClick -> {
                _state.update {
                    val products = it.productsInterest.toMutableList()
                    products.add(
                        PurchaseRequest(
                            productServiceName = it.productInterest,
                            purchaseDate = Clock.System.now().toEpochMilliseconds(),
                            amount = if (it.price.isEmpty()) 0.0 else it.price.replace(
                                "$",
                                ""
                            ).toDouble()
                        )
                    )
                    it.copy(
                        price = "",
                        productsInterest = products,
                        productInterest = "Selecione una opcion",
                    )
                }
            }

            AddLeadAction.OnSelectAnswerProductInterestClick -> {
                _state.update { it.copy(interestProduct = !it.interestProduct) }
            }

            is AddLeadAction.OnNextScreenClick -> {
                if (action.screen == ContainerState.FINISH) {
                    val dateReminder = _state.value.date + _state.value.time
                    if (validAvailableDate(dateReminder)) {
                        _state.update {
                            it.copy(
                                isDateAvailable = false,
                                showAvailableDayDialog = !it.showAvailableDayDialog
                            )
                        }
                    } else {
                        createCustomer()
                    }
                } else {
                    if (!validateAndSetErrors()) {
                        _state.update {
                            it.copy(
                                screenState = action.screen
                            )
                        }
                    }
                }
            }
            is AddLeadAction.OnTimeNextReminder -> {
                _state.update {
                    it.copy(
                        time = action.time
                    )
                }
            }
            AddLeadAction.OnToggleDateNoAvailable -> {
                _state.update {
                    it.copy(
                        showAvailableDayDialog = !it.showAvailableDayDialog
                    )
                }
            }
            AddLeadAction.OnSelectHasPendientDateClick -> {
                _state.update { it.copy(hasPendients = !it.hasPendients) }
            }

            is AddLeadAction.OnNoteChange -> {
                _state.update {
                    it.copy(
                        notes = action.note
                    )
                }
            }

            else -> Unit
        }
    }
    private fun getAllCustomers(){
        viewModelScope.launch(Dispatchers.IO){
            val result = customerRepository.getAllCustomers()
            when(result){
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            listAllCustomers = emptyList()
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        it.copy(
                            listAllCustomers = result.data
                        )
                    }
                }
            }
        }
    }

    private fun validAvailableDate(date: Long): Boolean {
        val margenMinimoMillis = 3_600_000 // 1 hora en milisegundos

        return _state.value.reminders.any { reminder ->
            // Calcula la diferencia en milisegundos entre el recordatorio y la fecha dada
            val diferenciaMillis = kotlin.math.abs(
                reminder.toInstant(TimeZone.UTC).toEpochMilliseconds() - date
            )

            // Valida si la diferencia es menor que el margen mínimo en milisegundos
            diferenciaMillis < margenMinimoMillis
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

    private fun createCustomer() {
        viewModelScope.launch(Dispatchers.IO) {
            val dateInteraction = _state.value.dateInteraction + _state.value.timeInteraction
            val reminderDate = _state.value.date + _state.value.time
            val result = customerRepository.create(
                companyName = _state.value.nameCompany,
                contactName = _state.value.contactName,
                email = _state.value.email,
                phoneNumber = _state.value.phoneNumber,
                address = _state.value.fiscalAddress,
                description = _state.value.descriptionNextAppointment,
                isOpportunity = _state.value.isOportunity,
                typeOfClient = _state.value.typeClient,
                purchese = _state.value.productsInterest,
                followUpTasks = "",
                interactionType = _state.value.interactionType,
                notes = _state.value.notes,
                reminderDate = reminderDate,
                isCompleted = false,
                potentialSale = 0.0,
                typeAppointment = _state.value.typeAppointment,
                interactionDate = if (dateInteraction > 0) dateInteraction else _state.value.dateInteration,
                status = "Nuevo"
            )
            when (result) {
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            screenState = ContainerState.ISERROR
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        it.copy(
                            nameCompany = "",
                            contactName = "",
                            screenState = ContainerState.ISSUCCESS
                        )
                    }
                }
            }
        }
    }

    /**
     * Validadores
     */

    //Funcion que verifica si puede inicar sesión
    private fun validateAndSetErrors(): Boolean {
        val razonSocialError = validNameCompany(_state.value.nameCompany)

        _state.update {
            it.copy(
                errorRazonSocial = razonSocialError
            )
        }
        return  razonSocialError != null
    }

    private fun validNameCompany(nameCompany: String): String? {
        return when {
            nameCompany.isEmpty() -> "El campo esta vacio"
            else -> null
        }
    }


}