package org.propapel.prospeccion.root.domain.models

data class Interaction(
    val interactionId: Int = 0,  // Relación con cliente
    val opportunityId: Int? = null,  // Relación opcional con oportunidad
    val interactionType:String,
    val interactionDate: Long = 0L,
    val notes: String? = null
)