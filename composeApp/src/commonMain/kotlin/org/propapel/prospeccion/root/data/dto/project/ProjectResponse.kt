package org.propapel.prospeccion.root.data.dto.project

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
    val customer: CustomerDto,
    val products: List<PurchaseResponseDto>?
)
