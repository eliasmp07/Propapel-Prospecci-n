package org.propapel.prospeccion.root.presentation.createInteraction

import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.presentation.createReminder.CreateReminderAction

sealed interface CreateInteractionAction {
    data class OnDateNextReminder(val date: Long): CreateInteractionAction
    data class OnTimeNextReminder(val time: Long): CreateInteractionAction
    data class OnNextScreen(val screen: CreateInteractionScreenState): CreateInteractionAction
    data object OnBackClick : CreateInteractionAction
    data class OnTypeClientChange(val typeClient: InteractionType): CreateInteractionAction
    data class OnNoteAppointmentChange(val notes: String): CreateInteractionAction
    data class OnProductInterestedChange(val product: String): CreateInteractionAction
    data class OnPriceChange(val price: String): CreateInteractionAction
    data object OnAddProductClick: CreateInteractionAction
    data class OnRemoveProductClick(val product: PurchaseRequest): CreateInteractionAction
}