package org.propapel.prospeccion.root.data.dto.reminder

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.propapel.prospeccion.root.data.dto.customer.InteractionDto
import org.propapel.prospeccion.root.data.dto.customer.OpportunityDto

@Serializable
data class CustomerDto(
    @SerialName("customer_id") val customerId: Int,
    @SerialName("company_name") val companyName: String,
    @SerialName("contact_name") val contactName: String, val email: String,
    @SerialName("phone_number") val phoneNumber: String, val address: String? = null,
    val progressLead: Double = 0.0,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("type_of_client") val typeClient: String,
    val opportunities: List<OpportunityDto>? = null,
    val interactions: List<InteractionDto>? = null,
    @SerialName("purchases")val purchases: List<PurchaseResponseDto>? = null,
    @SerialName("reminders")val reminders: List<ReminderDto2>? = null
)

@Serializable
data class PurchaseResponseDto(
    @SerialName("purchase_id") val purcheseId: Int,
    @SerialName("isIntoProduct") val isIntoProduct: Boolean,
    @SerialName("product_service_name")val productServiceName: String,
    @SerialName("purchase_date")val purchaseDate: Long,
    val amount: String
)

@Serializable
data class PurchaseResponseCreateDto(
    @SerialName("purchase_id") val purcheseId: Int,
    @SerialName("isIntoProduct") val isIntoProduct: Boolean,
    @SerialName("product_service_name")val productServiceName: String,
    @SerialName("purchase_date")val purchaseDate: Long,
    val amount: String
)