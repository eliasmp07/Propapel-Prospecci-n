package org.propapel.prospeccion.root.domain.models


data class Customer(
    val idCustomer: Int = 0,
    val companyName: String = "",
    val contactName: String = "",
    val email: String = "",
    val typeClient: String = "",
    val phoneNumber: String = "",
    val address: String? = null,
    val purchase: List<Purchase> = listOf(),
    val opportunities: List<Opportunity> = listOf(),
    val interactions: List<Interaction> = listOf(),
    val reminders: List<Reminder> = listOf(),
)