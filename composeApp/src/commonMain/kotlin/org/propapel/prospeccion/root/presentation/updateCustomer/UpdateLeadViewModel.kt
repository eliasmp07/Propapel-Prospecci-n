package org.propapel.prospeccion.root.presentation.updateCustomer

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
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.repository.CustomerRepository

class UpdateLeadViewModel(
    private val customerRepository: CustomerRepository
): ViewModel() {
    private var _state = MutableStateFlow(UpdateLeadState())
    val state: StateFlow<UpdateLeadState> get() = _state.asStateFlow()

    fun getLead(leadId: String){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    customerId = leadId,
                    isLoading = true
                )
            }
            val result = customerRepository.getCustomerById(leadId)
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
                            isLoading = false,
                            nameCompany = result.data.companyName,
                            name = result.data.contactName,
                            lastName = result.data.contactName,
                            email = result.data.email,
                            phone = result.data.phoneNumber,
                            address = result.data.address?:"",
                            typeOfClient = TypeOfClient.valueOf(result.data.typeClient)
                        )
                    }
                }
            }
        }
    }

    fun onAction(
        action: UpdateLeadAction
    ){
        when(action){
            is UpdateLeadAction.OnNameCompanyChange -> {
                _state.value = _state.value.copy(nameCompany = action.nameCompany)
            }
            is UpdateLeadAction.OnTypeOfClientChange -> {
                _state.value = _state.value.copy(typeOfClient = action.typeOfClient)
            }
            is UpdateLeadAction.OnUpdateAddress -> {
                _state.value = _state.value.copy(address = action.address)
            }
            is UpdateLeadAction.OnUpdateEmailChange -> {
                _state.value = _state.value.copy(email = action.email)
            }
            is UpdateLeadAction.OnUpdateLastNameChange -> {
                _state.value = _state.value.copy(lastName = action.lastName)
            }
            is UpdateLeadAction.OnUpdateNameChange -> {
                _state.value = _state.value.copy(name = action.name)
            }
            is UpdateLeadAction.OnUpdatePhoneChange -> {
                _state.value = _state.value.copy(phone = action.phone)
            }
            UpdateLeadAction.UpdateLeadClick -> {
                updateCustomer()
            }
            UpdateLeadAction.OnRetryClick -> {
                getLead(_state.value.customerId)
            }
            UpdateLeadAction.HideError -> {
                _state.update {
                    it.copy(
                        isErrorUpdateLead = !it.isErrorUpdateLead,
                    )
                }
            }
            UpdateLeadAction.HideSuccess -> {
                _state.update {
                    it.copy(
                        isSuccess = !it.isSuccess,
                        success = ""
                    )
                }
            }
        }
    }
    private fun updateCustomer(){
        viewModelScope.launch(Dispatchers.IO){
            _state.update {
                it.copy(
                    errorUpdateLead = null,
                    isUpdatingLead = true
                )
            }
            val result = customerRepository.updateCustomer(
                Customer(
                    idCustomer = _state.value.customerId.toInt(),
                    companyName = _state.value.nameCompany,
                    contactName = _state.value.name + " " + _state.value.lastName,
                    email = _state.value.email,
                    phoneNumber = _state.value.phone,
                    address = _state.value.address,
                    typeClient = _state.value.typeOfClient.name
                )
            )
            when(result){
                is ResultExt.Error -> {
                    _state.update {
                        it.copy(
                            isUpdatingLead = false,
                            isErrorUpdateLead = true,
                            errorUpdateLead = result.error.asUiText()
                        )
                    }
                }
                is ResultExt.Success -> {
                    _state.update {
                        it.copy(
                            isSuccess = true,
                            success = "Cliente actualizado correctamente",
                            errorUpdateLead = null,
                            isUpdatingLead = false,
                            nameCompany = "",
                            name = "",
                            lastName = "",
                            email = "",
                            phone = "",
                            address = "",
                            typeOfClient = TypeOfClient.NUEVO
                        )
                    }
                }
            }
        }
    }
}