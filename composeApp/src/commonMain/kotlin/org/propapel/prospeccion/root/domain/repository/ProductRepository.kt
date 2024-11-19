package org.propapel.prospeccion.root.domain.repository

import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.domain.models.Purchase

interface ProductRepository {
    suspend fun create(
        customerId: Int,
        productServiceName: String,
        purchaseDate: Long,
        amount: Double
    ): ResultExt<Purchase, DataError.Network>
}