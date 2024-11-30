package org.propapel.prospeccion.selectSucursal.domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class CustomerUser(
    val address: String,
    val companyName: String,
    val contactName: String,
    val createdAt: LocalDateTime,
    val customerId: Int,
    val email: String,
    val interactions: List<InteractionUser>,
    val phoneNumber: String,
    val progressLead: String,
    val projects: List<ProjectUser>,
    val purchaseUsers: List<PurchaseUser>,
    val reminderUsers: List<ReminderUser>,
    val typeOfClient: String,
    val updatedAt: String,
    val user: User
){
    companion object{
        fun stringToLocalDateTime(dateString: String): LocalDateTime {
            val formatter = kotlinx.datetime.Instant.parse(dateString).toLocalDateTime(TimeZone.currentSystemDefault())
            return formatter
        }
    }
}