package org.propapel.prospeccion.root.presentation.completeReminder

import androidx.compose.runtime.Stable
import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.presentation.completeReminder.components.CompleteReminderScreenState

@Stable
data class CompleteReminderState(
    val screenState: CompleteReminderScreenState = CompleteReminderScreenState.ADD_INFO_INTERVIEW,
    val interactionType: InteractionType = InteractionType.LLAMADA,
    val productInterest: String = "Seleccione una opción",
    val typeDate: InteractionType = InteractionType.PRESENCIAL,
    val price: String = "",
    val productsInterest: List<PurchaseRequest> = listOf(),
    val notes: String = ""
)
