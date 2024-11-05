package org.propapel.prospeccion.root.data.dto.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateCustomerRequest(
    @SerialName("company_name") val companyName: String,
    @SerialName("contact_name") val contactName: String,
    @SerialName("email") val email: String,
    @SerialName("type_of_client") val typeOfClient: String,
    @SerialName("phone_number") val phoneNumber: String,
    @SerialName("address") val address: String? = null
)