package org.propapel.prospeccion.root.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PurchaseCreateRequest(
    val customerId: Int,  // Relación con cliente
    @SerialName("product_service_name")val productServiceName: String,
    @SerialName("purchase_date") val purchaseDate: Long,
    val amount: Double = 0.0
)