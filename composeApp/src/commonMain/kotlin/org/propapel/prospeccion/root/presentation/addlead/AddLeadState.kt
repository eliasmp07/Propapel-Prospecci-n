package org.propapel.prospeccion.root.presentation.addlead

import kotlinx.datetime.Clock
import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.domain.models.Reminder
import org.propapel.prospeccion.root.presentation.addlead.components.utils.ProductsPropapel

data class AddLeadState(
    val nameCompany: String = "",
    val contactName: String = "",
    val task: String = "Lead Nuevo",
    val status: String = "",
    val isOtherDate: Boolean = false,
    val hasPendients: Boolean = false,
    val tasks: List<String> = listOf(),
    val typeClient: TypeOfClient = TypeOfClient.NUEVO,
    val screenState: ContainerState = ContainerState.ADDLEAD,
    val email: String = "",
    val phoneNumber: String = "",
    val fiscalAddress: String = "",
    val interestProduct: Boolean = false,
    val productsInterest: List<PurchaseRequest> = listOf(),
    val productInterest: ProductsPropapel = ProductsPropapel.PAPELERIA,
    //Reminder
    val dateNextReminder: Long = Clock.System.now().toEpochMilliseconds(),
    val descriptionNextAppointment: String = "",
    //Oportunity
    val isOportunity: Boolean = false,
    //Interation Info
    val price: String = "",

    val typeDate: InteractionType = InteractionType.PRESENCIAL,
    val interactionType: InteractionType = InteractionType.LLAMADA,
    val dateInteration: Long = Clock.System.now().toEpochMilliseconds(),
    val notes: String = "",

    val reminders: List<Reminder> = listOf()

)