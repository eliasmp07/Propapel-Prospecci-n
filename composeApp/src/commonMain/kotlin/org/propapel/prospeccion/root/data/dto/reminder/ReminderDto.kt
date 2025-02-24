package org.propapel.prospeccion.root.data.dto.reminder

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReminderResponseDto(
    @SerialName("reminder_id")  val reminderId: Int,
    @SerialName("reminder_date") val reminderDate: String,
    @SerialName("description")val description: String,
    @SerialName("is_completed")val isComplete: Boolean,
    @SerialName("typeAppointment") val typeAppointment: String,
    @SerialName("customer") val customer: CustomerReminderDto?,
    @SerialName("created_at") val createdAt: String?,
    @SerialName("updated_at") val updatedAt: String?
){
    fun stringToLocalDateTime(dateString: String): LocalDateTime {
        val formatter = kotlinx.datetime.Instant.parse(dateString).toLocalDateTime(TimeZone.currentSystemDefault())
        return formatter
    }
}

@Serializable
data class CustomerReminderDto(
    @SerialName("customer_id") val customerId: Int,
    @SerialName("company_name") val companyName: String,
    @SerialName("contact_name") val contactName: String,
    val progressLead: Double,
    @SerialName("email")val email: String,
    @SerialName("phone_number") val phoneNumber: String,
    @SerialName("type_of_client") val typeClient: String,
    @SerialName("address")val address: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

@Serializable
data class ReminderDto2(
    @SerialName("typeAppointment") val typeAppointment: String,
    @SerialName("created_at") val createdAt: String?,
    @SerialName("updated_at") val updatedAt: String?,
    @SerialName("reminder_id")  val reminderId: Int,
    @SerialName("reminder_date") val reminderDate: String,
    val description: String,
    @SerialName("is_completed")val isComplete: Boolean
){
    fun stringToLocalDateTime(dateString: String): LocalDateTime {
        val formatter = kotlinx.datetime.Instant.parse(dateString).toLocalDateTime(TimeZone.currentSystemDefault())
        return formatter
    }
}