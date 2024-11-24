package org.propapel.prospeccion.root.presentation.addlead

import org.propapel.prospeccion.root.data.dto.customer.InteractionType
import org.propapel.prospeccion.root.data.dto.customer.TypeOfClient
import org.propapel.prospeccion.root.domain.models.PurchaseRequest

sealed interface AddLeadAction {

    data class OnFullNameChange(val fullName: String): AddLeadAction
    data class OnRazonSocialChange(val razonSocial: String): AddLeadAction
    data class OnEmailChange(val email: String): AddLeadAction
    data class OnPhoneNumberChange(val phoneNumber: String): AddLeadAction
    data class OnAddressFiscalChange(val fiscalAddress: String): AddLeadAction

    //Oportunity Screen
    data class OnResponseOportunityChange(val response: Boolean): AddLeadAction

    //Remider
    data class OnDateNextReminder(val date: Long): AddLeadAction
    data class OnDescriptionNextReminderChange(val description: String): AddLeadAction
    data object OnToggleDateNoAvailable: AddLeadAction
    data class OnTypeAppointmentChange(val typeAppointment: String): AddLeadAction

    data class OnNoteChange(val note: String): AddLeadAction

    data class OnTaskChange(val task: String): AddLeadAction
    data object OnSaveTaskClick: AddLeadAction
    data class OnRemoveTaskClick(val task: String): AddLeadAction
    data class OnRemoveProductInterestClick(val product: PurchaseRequest): AddLeadAction
    data object OnSelectIsOtherDateClick: AddLeadAction
    data object OnSelectAnswerProductInterestClick: AddLeadAction
    data object OnSelectHasPendientDateClick: AddLeadAction

    data class OnTypeClientChange(val typeClient: TypeOfClient): AddLeadAction
    data class OnTypeDateChange(val typeInteraction: InteractionType): AddLeadAction
    data class OnProductInterestChange(val product: String): AddLeadAction

    data object OnAddProductClick : AddLeadAction

    data class OnPriceChange(val price: String): AddLeadAction

    data object OnBackClick: AddLeadAction
    data class OnNextScreenClick(val screen: ContainerState): AddLeadAction
}