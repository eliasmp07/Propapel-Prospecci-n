package org.propapel.prospeccion.root.domain.models

data class Opportunity(
    val customerId: Int,  // Relación con cliente
    val isOpportunity: Boolean,
    val potentialSale: Double,
    val status: String,
    val followUpTasks: String? = null
)