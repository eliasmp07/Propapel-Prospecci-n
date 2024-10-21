package org.propapel.prospeccion.root.data.dto.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateCustomerRequest(
    @SerialName("company_name") val companyName: String,
    @SerialName("contact_name") val contactName: String,
    val email: String,
    @SerialName("phone_number") val phoneNumber: String,
    val address: String? = null,
    @SerialName("type_of_client") val typeClient: String = "",
    val idUser: Int,
    val opportunities: List<OpportunityDto>? = null,
    val interactions: List<InteractionDto>? = null,
    val purchases: List<CreatePurchaseRequest>? = null,
    val reminders: List<ReminderRequest>? = null
)