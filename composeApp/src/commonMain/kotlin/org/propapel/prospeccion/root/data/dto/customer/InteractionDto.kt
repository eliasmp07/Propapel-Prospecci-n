package org.propapel.prospeccion.root.data.dto.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InteractionDto(
    @SerialName("interaction_id")val interactionId: Int,  // Relación con cliente
    val opportunityId: Int? = null,  // Relación opcional con oportunidad
    @SerialName("interaction_type") val interactionType: String,
    @SerialName("interaction_date") val interactionDate: Long,
    val notes: String? = null
)

@Serializable
data class InteractionResponseDto(
    @SerialName("interaction_id")val interactionId: Int,  // Relación con cliente
    @SerialName("interaction_type") val interactionType: String,
    @SerialName("interaction_date") val interactionDate: Long,
    val notes: String? = null
)

@Serializable
enum class InteractionType {
    PRESENCIAL, LLAMADA, REUNION_REMOTA, EMAIL
}