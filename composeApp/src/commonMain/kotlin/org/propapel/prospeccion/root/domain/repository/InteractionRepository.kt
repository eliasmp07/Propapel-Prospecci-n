package org.propapel.prospeccion.root.domain.repository

import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.domain.models.Interaction
import org.propapel.prospeccion.root.domain.models.PurchaseRequest

interface InteractionRepository {
    suspend fun create(idCustomer: String, interaction: Interaction, purchese: List<PurchaseRequest>,): ResultExt<Interaction, DataError.Network>
}