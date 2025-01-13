package org.propapel.prospeccion.root.presentation.createInteraction

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.domain.models.PurchaseRequest

data class CreateInteractionLeadState(
    val screenState: CreateInteractionScreenState = CreateInteractionScreenState.InfoInteractionScreen,
    val typeClient : InteractionType = InteractionType.PRESENCIAL,
    val idCustomer: String = "",
    val reminderId: Int = 0,
    val notesAppointment: String = "",
    val productsIntereses: List<PurchaseRequest> = emptyList(),
    val productInterested: String = "Seleccione una opción",
    val price : String = "",
    val isCreatingInteraction: Boolean = false,
    val isSuccessCreate: Boolean = false,
    val time: Long = 0L,
    val date: Long = 0L,
)
