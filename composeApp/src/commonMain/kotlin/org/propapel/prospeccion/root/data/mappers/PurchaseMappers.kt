package org.propapel.prospeccion.root.data.mappers

import org.propapel.prospeccion.root.data.dto.project.PurchasesWithAmountUpdate
import org.propapel.prospeccion.root.domain.models.Purchase

fun Purchase.toPurchasesWithAmountUpdate(): PurchasesWithAmountUpdate{
    return PurchasesWithAmountUpdate(
        amount = amount.toDouble(),
        purchaseId = purcheseId
    )
}