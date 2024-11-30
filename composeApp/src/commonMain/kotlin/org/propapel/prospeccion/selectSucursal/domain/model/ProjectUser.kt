package org.propapel.prospeccion.selectSucursal.domain.model

import kotlinx.datetime.LocalDateTime

data class ProjectUser(
    val createdAt: LocalDateTime,
    val id: Int,
    val isCancel: Boolean,
    val nameProject: String,
    val prioridad: String,
    val progress: Int,
    val status: String,
    val updatedAt: String,
    val valorProject: String
)