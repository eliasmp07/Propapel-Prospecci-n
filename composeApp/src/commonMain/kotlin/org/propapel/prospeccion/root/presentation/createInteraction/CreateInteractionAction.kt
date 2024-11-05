package org.propapel.prospeccion.root.presentation.createInteraction

import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.presentation.addlead.components.utils.ProductsPropapel

sealed interface CreateInteractionAction {
    data class OnNextScreen(val screen: CreateInteractionScreenState): CreateInteractionAction
    data object OnBackClick : CreateInteractionAction
    data class OnTypeClientChange(val typeClient: InteractionType): CreateInteractionAction
    data class OnNoteAppointmentChange(val notes: String): CreateInteractionAction
    data class OnProductInterestedChange(val product: ProductsPropapel): CreateInteractionAction
    data class OnPriceChange(val price: String): CreateInteractionAction
    data object OnAddProductClick: CreateInteractionAction
    data class OnRemoveProductClick(val product: PurchaseRequest): CreateInteractionAction
}