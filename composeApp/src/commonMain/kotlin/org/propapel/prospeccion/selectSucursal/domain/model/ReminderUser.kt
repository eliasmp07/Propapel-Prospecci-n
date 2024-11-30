package org.propapel.prospeccion.selectSucursal.domain.model

data class ReminderUser(
    val createdAt: String,
    val description: String,
    val isCompleted: Boolean,
    val reminderDate: String,
    val reminderId: Int,
    val typeAppointment: String,
    val updatedAt: String
)