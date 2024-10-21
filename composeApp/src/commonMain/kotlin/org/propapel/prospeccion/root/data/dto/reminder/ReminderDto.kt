package org.propapel.prospeccion.root.data.dto.reminder

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.propapel.prospeccion.root.data.dto.customer.InteractionDto
import org.propapel.prospeccion.root.data.dto.customer.OpportunityDto
import org.propapel.prospeccion.root.domain.models.Customer

@Serializable
data class ReminderResponseDto(
    @SerialName("reminder_id")  val reminderId: Int,
    @SerialName("reminder_date") val reminderDate: String,
    @SerialName("description")val description: String,
    @SerialName("is_completed")val isComplete: Boolean,
    @SerialName("customer") val customer: CustomerReminderDto,
)

@Serializable
data class CustomerReminderDto(
    @SerialName("customer_id") val customerId: Int,
    @SerialName("company_name") val companyName: String,
    @SerialName("contact_name") val contactName: String,
    @SerialName("email")val email: String,
    @SerialName("phone_number") val phoneNumber: String,
    @SerialName("type_of_client") val typeClient: String,
    @SerialName("address")val address: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

@Serializable
data class ReminderDto2(
    @SerialName("reminder_id")  val reminderId: Int,
    @SerialName("reminder_date") val reminderDate: String,
    val description: String,
    @SerialName("is_completed")val isComplete: Boolean
)