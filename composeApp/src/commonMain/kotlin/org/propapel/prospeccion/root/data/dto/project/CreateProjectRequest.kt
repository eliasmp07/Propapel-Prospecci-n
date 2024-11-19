package org.propapel.prospeccion.root.data.dto.project

import kotlinx.serialization.Serializable

@Serializable
data class CreateProjectRequest(
    val nameProject: String,
    val valorProject: Double,
    val progress: Int,
    val state: String,
    val prioridad: String,
    val customerId: String,
    val purchases: List<PurchasesWithAmountUpdate>?
)

@Serializable
data class PurchasesWithAmountUpdate(
    val amount: Double,
    val purchaseId: Int
)