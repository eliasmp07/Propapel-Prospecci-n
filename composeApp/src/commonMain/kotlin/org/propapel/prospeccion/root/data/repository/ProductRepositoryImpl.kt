package org.propapel.prospeccion.root.data.repository

import io.ktor.client.HttpClient
import org.propapel.prospeccion.core.data.networking.post
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.data.dto.PurchaseCreateRequest
import org.propapel.prospeccion.root.data.dto.reminder.PurchaseResponseCreateDto
import org.propapel.prospeccion.root.data.mappers.toPurchase
import org.propapel.prospeccion.root.domain.models.Purchase
import org.propapel.prospeccion.root.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val httpClient: HttpClient
): ProductRepository {
    override suspend fun create(
        customerId: Int,
        productServiceName: String,
        purchaseDate: Long,
        amount: Double
    ): ResultExt<Purchase, DataError.Network> {
        val result = httpClient.post<PurchaseCreateRequest, PurchaseResponseCreateDto>(
            route = "/purchase/create",
            body = PurchaseCreateRequest(
                customerId = customerId,
                purchaseDate = purchaseDate,
                productServiceName = productServiceName,
                amount = amount
            )
        )

        return when(result){
            is ResultExt.Error -> {
                ResultExt.Error(result.error)
            }
            is ResultExt.Success -> {
                ResultExt.Success(result.data.toPurchase())
            }
        }
    }
}