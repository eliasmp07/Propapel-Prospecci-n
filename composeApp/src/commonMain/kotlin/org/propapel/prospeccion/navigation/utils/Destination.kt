package org.propapel.prospeccion.navigation.utils

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable
    data object AuthGraph: Destination

    @Serializable
    data object IntroScreen: Destination

    @Serializable
    data object CreateReminder: Destination

    @Serializable
    data object LoginScreen: Destination

    @Serializable
    data object ProSales: Destination

    @Serializable
    data object DashBoard: Destination

    @Serializable
    data class CompleteReminder(val reminderId: String): Destination

    @Serializable
    data object AddLead: Destination

    @Serializable
    data object SelectSucursal: Destination

    @Serializable
    data class UpdateLead(val customerId: String): Destination

    @Serializable
    data object EditProfile: Destination

    @Serializable
    data object SearchLead: Destination
    @Serializable
    data class DetailCustomer(val idCustomer: String)

    @Serializable
    data class DetailReminderCustomer(val idReminder: String)

    @Serializable
    data class CreateInteraction(val customerId: String): Destination

    @Serializable
    data class CreateProject(val customerId: String): Destination

}
