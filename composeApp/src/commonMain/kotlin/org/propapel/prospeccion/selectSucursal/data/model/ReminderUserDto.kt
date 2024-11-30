package org.propapel.prospeccion.selectSucursal.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReminderUserDto(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("description")
    val description: String,
    @SerialName("is_completed")
    val isCompleted: Boolean,
    @SerialName("reminder_date")
    val reminderDate: String,
    @SerialName("reminder_id")
    val reminderId: Int,
    @SerialName("typeAppointment")
    val typeAppointment: String,
    @SerialName("updated_at")
    val updatedAt: String
)