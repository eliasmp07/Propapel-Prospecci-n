package org.propapel.prospeccion.root.presentation.updateCustomer

import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient

sealed interface UpdateLeadAction {

    data object OnBackClick : UpdateLeadAction
    data class OnUpdateNameChange(val name: String) : UpdateLeadAction
    data class OnUpdateEmailChange(val email: String) : UpdateLeadAction
    data class OnUpdatePhoneChange(val phone: String) : UpdateLeadAction
    data class OnUpdateAddress(val address: String) : UpdateLeadAction
    data class OnTypeOfClientChange(val typeOfClient: TypeOfClient) : UpdateLeadAction
    data object UpdateLeadClick : UpdateLeadAction
    data class OnNameCompanyChange(val nameCompany: String) : UpdateLeadAction
    data object OnRetryClick : UpdateLeadAction

    data object HideError : UpdateLeadAction
    data object HideSuccess : UpdateLeadAction
}