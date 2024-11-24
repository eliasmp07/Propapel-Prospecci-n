package org.propapel.prospeccion.root.presentation.createInteraction

import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.domain.models.PurchaseRequest

data class CreateInteractionLeadState(
    val screenState: CreateInteractionScreenState = CreateInteractionScreenState.InfoInteractionScreen,
    val typeClient : InteractionType = InteractionType.PRESENCIAL,
    val idCustomer: String = "",
    val notesAppointment: String = "",
    val productsIntereses: List<PurchaseRequest> = emptyList(),
    val productInterested: String = "Seleccione una opción",
    val price : String = "",
    val isCreatingInteraction: Boolean = false,
    val isSuccessCreate: Boolean = false
)
