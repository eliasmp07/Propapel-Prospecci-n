package org.propapel.prospeccion.root.data.dto.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePurchaseRequest(
    val customerId: Int,  // Relación con cliente
    @SerialName("product_service_name") val productServiceName: String,
    @SerialName("purchase_date") val purchaseDate: Long,
    val amount: Double
)