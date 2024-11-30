package org.propapel.prospeccion.selectSucursal.domain.model

data class InteractionUser(
    val interactionDate: String,
    val interactionId: Int,
    val interactionType: String,
    val notes: String
)