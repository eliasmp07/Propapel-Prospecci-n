package org.propapel.prospeccion.root.data.dto.customer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.propapel.prospeccion.root.data.dto.reminder.CustomerDto

@Serializable
data class PurchasesDto(
    val purchaseId: Int,
    val productServiceName: String,
    val amount: Double,
    val customerDto: CustomerDto,
    val purchaseDate: Long
)
