package org.propapel.prospeccion.root.data.dto.reminder

import kotlinx.serialization.Serializable

@Serializable
data class ReminderResponse(
    val reminders: List<ReminderResponseDto>
)
