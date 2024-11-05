package org.propapel.prospeccion.root.data.mappers

import org.propapel.prospeccion.root.data.dto.reminder.CustomerReminderDto
import org.propapel.prospeccion.root.data.dto.reminder.ReminderResponseDto
import org.propapel.prospeccion.root.domain.models.Customer
import org.propapel.prospeccion.root.domain.models.Reminder

fun ReminderResponseDto.toReminder(): Reminder {
    return Reminder(
        reminderDate = reminderDate,
        reminderId = reminderId,
        description = description,
        customer = customer?.toCustomer()?:Customer(),
        isCompleted = isComplete
    )
}

fun CustomerReminderDto.toCustomer(): Customer {
    return Customer(
        companyName = companyName,
        contactName = contactName,
        email = email,
        phoneNumber = phoneNumber,
        address = address,
        idCustomer = customerId,
        typeClient = typeClient
    )
}