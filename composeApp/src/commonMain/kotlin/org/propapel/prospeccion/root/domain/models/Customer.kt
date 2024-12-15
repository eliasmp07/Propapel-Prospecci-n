package org.propapel.prospeccion.root.domain.models


data class Customer(
    val idCustomer: Int = 0,
    val companyName: String = "",
    val contactName: String = "",
    val tags: List<String> = listOf(),
    val email: String = "",
    val progressLead: Double = 0.0,
    val typeClient: String = "",
    val phoneNumber: String = "",
    val address: String? = null,
    val purchase: List<Purchase> = listOf(),
    val opportunities: List<Opportunity> = listOf(),
    val interactions: List<Interaction> = listOf(),
    val reminders: List<Reminder> = listOf(),
)