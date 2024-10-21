package org.propapel.prospeccion.root.data.mappers

import org.propapel.prospeccion.root.data.dto.customer.CreatePurchaseRequest
import org.propapel.prospeccion.root.data.dto.customer.InteractionDto
import org.propapel.prospeccion.root.data.dto.customer.OpportunityDto
import org.propapel.prospeccion.root.data.dto.reminder.CustomerDto
import org.propapel.prospeccion.root.data.dto.reminder.CustomerReminderDto
import org.propapel.prospeccion.root.data.dto.reminder.PurchasesDto2
import org.propapel.prospeccion.root.data.dto.reminder.ReminderDto2
import org.propapel.prospeccion.root.data.dto.reminder.ReminderResponseDto
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Interaction
import org.propapel.prospeccion.root.domain.models.Opportunity
import org.propapel.prospeccion.root.domain.models.Purchase
import org.propapel.prospeccion.root.domain.models.PurchaseRequest
import org.propapel.prospeccion.root.domain.models.Reminder

fun CustomerDto.toCustomer():Customer{
    return Customer(
        companyName = companyName,
        contactName = contactName,
        email = email,
        phoneNumber = phoneNumber,
        address = address,
        idCustomer = customerId,
        interactions = interactions?.map {
            it.toInteraction()
        }?: listOf(),
        purchase = purchases?.map {
            it.toPurchase()
        }?: listOf(),
        opportunities = opportunities?.map {
            it.toOpportunity()
        }?: listOf(),
        reminders = reminders?.map {
            it.toReminder()
        }?: listOf(),
        typeClient = typeClient
    )
}

fun PurchasesDto2.toPurchase(): Purchase{
    return Purchase(
        purchaseDate = purchaseDate,
        productServiceName = productServiceName,
        purcheseId = purcheseId,
        amount = amount
    )
}

fun PurchaseRequest.toPurcheDto(): CreatePurchaseRequest{
    return CreatePurchaseRequest(
        customerId = customerId,
        purchaseDate = purchaseDate,
        productServiceName = productServiceName,
        amount = amount

    )
}
fun ReminderDto2.toReminder(): Reminder{
    return Reminder(
        reminderDate = reminderDate,
        reminderId = reminderId,
        description = description,
        isCompleted = isComplete
    )
}


fun InteractionDto.toInteraction(): Interaction{
    return Interaction(
        interactionId, opportunityId, interactionType, interactionDate, notes
    )
}

fun OpportunityDto.toOpportunity(): Opportunity{
    return Opportunity(
        opportunityId, isOpportunity, potentialSale, status
    )
}