package org.propapel.prospeccion.selectSucursal.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectUserDto(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("id")
    val id: Int,
    @SerialName("isCancel")
    val isCancel: Boolean,
    @SerialName("nameProject")
    val nameProject: String,
    @SerialName("prioridad")
    val prioridad: String,
    @SerialName("progress")
    val progress: Int,
    @SerialName("status")
    val status: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("valorProject")
    val valorProject: String
)