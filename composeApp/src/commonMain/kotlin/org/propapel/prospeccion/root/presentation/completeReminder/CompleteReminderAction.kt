package org.propapel.prospeccion.root.presentation.completeReminder

import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.presentation.completeReminder.components.CompleteReminderScreenState

sealed interface CompleteReminderAction {
    data object OnBackClick: CompleteReminderAction
    data class OnNoteChange(val note: String): CompleteReminderAction
    data class OnTypeDateChange(val typeInteraction: InteractionType): CompleteReminderAction
    data class OnProductInterestChange(val product: String): CompleteReminderAction
    data object OnAddProductClick : CompleteReminderAction
    data class OnPriceChange(val price: String): CompleteReminderAction
    data class OnNextScreenClick(val screen: CompleteReminderScreenState): CompleteReminderAction
    data class OnRemoveProductInterestClick(val product: PurchaseRequest): CompleteReminderAction
}