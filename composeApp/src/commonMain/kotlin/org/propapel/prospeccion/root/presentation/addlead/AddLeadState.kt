package org.propapel.prospeccion.root.presentation.addlead

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.domain.models.PurchaseRequest

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
    val productInterest: String = "Seleccione una opción",
    //Reminder
    val dateNextReminder: Long = Clock.System.now().toLocalDateTime(TimeZone.UTC).toInstant(TimeZone.UTC).toEpochMilliseconds(),
    val descriptionNextAppointment: String = "",
    val typeAppointment: String = "",
    //Oportunity
    val isOportunity: Boolean = false,
    //Interation Info
    val price: String = "",

    val typeDate: InteractionType = InteractionType.PRESENCIAL,
    val interactionType: InteractionType = InteractionType.LLAMADA,
    val dateInteration: Long = Clock.System.now().toEpochMilliseconds(),
    val notes: String = "",
    val isDateAvailable: Boolean = false,
    val reminders: List<LocalDateTime> = listOf(),
    val reminderNoAvailable: List<LocalDateTime> = listOf(),
    val showAvailableDayDialog: Boolean = false,
    //Error textFieds
    val errorRazonSocial: String? = null,
    val errorNameCompany: String? = null,
    val errorEmailLead: String? = null,
    val errorPhoneNumber: String? = null,

    )