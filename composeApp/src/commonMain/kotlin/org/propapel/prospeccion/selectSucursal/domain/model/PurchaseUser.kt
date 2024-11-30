package org.propapel.prospeccion.selectSucursal.domain.model

data class PurchaseUser(
    val amount: String,
    val isIntoProduct: Boolean,
    val productServiceName: String,
    val purchaseDate: String,
    val purchaseId: Int
)