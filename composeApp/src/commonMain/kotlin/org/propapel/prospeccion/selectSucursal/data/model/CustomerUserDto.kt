package org.propapel.prospeccion.selectSucursal.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerUserDto(
    @SerialName("address")
    val address: String?,
    @SerialName("company_name")
    val companyName: String,
    @SerialName("contact_name")
    val contactName: String?,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("customer_id")
    val customerId: Int,
    @SerialName("email")
    val email: String?,
    @SerialName("interactions")
    val interactions: List<InteractionUserDto>,
    @SerialName("phone_number")
    val phoneNumber: String?,
    @SerialName("progressLead")
    val progressLead: String,
    @SerialName("projects")
    val projects: List<ProjectUserDto>?,
    @SerialName("purchases")
    val purchases: List<PurchaseUserDto>?,
    @SerialName("reminders")
    val reminders: List<ReminderUserDto>?,
    @SerialName("type_of_client")
    val typeOfClient: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("user")
    val user: UserDto
)