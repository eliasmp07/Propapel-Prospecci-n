package org.propapel.prospeccion.root.domain.models

import kotlinx.datetime.LocalDateTime

data class Reminder(
    val reminderId: Int = 0,  // Relación con cliente
    val reminderDate: String = "",
    val customer: Customer = Customer(),
    val description: String = "",
    val isCompleted: Boolean? = null
)
