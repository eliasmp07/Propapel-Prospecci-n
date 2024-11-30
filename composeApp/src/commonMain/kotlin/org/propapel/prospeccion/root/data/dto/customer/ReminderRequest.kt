package org.propapel.prospeccion.root.data.dto.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReminderRequest(
    val customerId: Int,  // Relación con cliente
    val opportunityId: Int? = null,  // Relación opcional con oportunidad
    @SerialName("reminder_date") val reminderDate: Long,
    val typeAppointment: String,
    val description: String,
    @SerialName("is_completed") val isCompleted: Boolean? = null
)