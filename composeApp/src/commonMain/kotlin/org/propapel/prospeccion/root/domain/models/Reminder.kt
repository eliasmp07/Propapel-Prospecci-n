package org.propapel.prospeccion.root.domain.models

import kotlinx.datetime.LocalDateTime
import network.chaintech.kmp_date_time_picker.utils.now

data class Reminder(
    val reminderId: Int = 0,  // Relación con cliente
    val reminderDate: String = "",
    val typeAppointment: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val customer: Customer = Customer(),
    val description: String = "",
    val isCompleted: Boolean? = null
)
