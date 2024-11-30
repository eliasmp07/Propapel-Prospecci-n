package org.propapel.prospeccion.selectSucursal.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InteractionUserDto(
    @SerialName("interaction_date")
    val interactionDate: String,
    @SerialName("interaction_id")
    val interactionId: Int,
    @SerialName("interaction_type")
    val interactionType: String,
    @SerialName("notes")
    val notes: String
)