package org.propapel.prospeccion.root.presentation.updateCustomer

import org.propapel.prospeccion.core.presentation.ui.UiText
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.domain.models.Customer

data class UpdateLeadState(
    val isLoading: Boolean = false,
    val isUpdatingLead: Boolean = false,
    val customerId: String = "",
    val isErrorUpdateLead: Boolean = false,
    val errorUpdateLead: UiText? = null,
    val error: UiText? = null,
    val isSuccess: Boolean = false,
    val success: String = "",
    val nameCompany: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val typeOfClient: TypeOfClient = TypeOfClient.NUEVO

    )
