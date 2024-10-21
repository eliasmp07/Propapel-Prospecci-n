package org.propapel.prospeccion.root.domain.models

import kotlinx.datetime.LocalDateTime
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient

data class Opportunity(
    val customerId: Int,  // Relación con cliente
    val isOpportunity: Boolean,
    val potentialSale: Double,
    val status: String,
    val followUpTasks: String? = null
)