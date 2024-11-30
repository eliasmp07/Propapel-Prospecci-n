package org.propapel.prospeccion.selectSucursal.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PurchaseUserDto(
    @SerialName("amount")
    val amount: String,
    @SerialName("isIntoProduct")
    val isIntoProduct: Boolean,
    @SerialName("product_service_name")
    val productServiceName: String,
    @SerialName("purchase_date")
    val purchaseDate: String,
    @SerialName("purchase_id")
    val purchaseId: Int
)