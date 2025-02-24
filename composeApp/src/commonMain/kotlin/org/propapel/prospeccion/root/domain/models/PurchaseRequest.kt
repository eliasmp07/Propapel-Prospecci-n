package org.propapel.prospeccion.root.domain.models

data class PurchaseRequest(
    val customerId: Int = 0,  // Relación con cliente
    val productServiceName: String = "",
    val purchaseDate: Long = 0,
    val amount: Double = 0.0
)

data class Purchase(
    val purcheseId: Int,
    val productServiceName: String,
    val isIntoProduct: Boolean,
    val purchaseDate: Long,
    val amount: String
)

