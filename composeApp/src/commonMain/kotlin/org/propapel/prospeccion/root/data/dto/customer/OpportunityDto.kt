package org.propapel.prospeccion.root.data.dto.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpportunityDto(
    @SerialName("opportunity_id")val opportunityId: Int,  // Relación con cliente
    @SerialName("is_opportunity") val isOpportunity: Boolean,
    @SerialName("potential_sale") val potentialSale: Double,
    val status: String,
    @SerialName("follow_up_tasks")val followUpTasks: String? = null
)

@Serializable
enum class TypeOfClient {
    NUEVO, RECUPERACIÓN, DESARROLLO
}