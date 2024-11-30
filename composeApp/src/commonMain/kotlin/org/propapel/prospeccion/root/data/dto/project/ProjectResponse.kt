package org.propapel.prospeccion.root.data.dto.project

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.propapel.prospeccion.root.data.dto.reminder.CustomerDto
import org.propapel.prospeccion.root.data.dto.reminder.PurchaseResponseDto

@Serializable
data class ProjectResponse(
    val id: Int,
    val nameProject: String,
    val valorProject: String,
    val progress: Int,
    val prioridad: String?,
    val status: String,
    val isCancel: Boolean,
    @SerialName("created_at") val createdAt: String,
    val customer: CustomerDto,
    val products: List<PurchaseResponseDto>?
){
    fun stringToLocalDateTime(dateString: String): LocalDate {
        val formatter = kotlinx.datetime.Instant.parse(dateString).toLocalDateTime(TimeZone.UTC)
        return formatter.date
    }

}
