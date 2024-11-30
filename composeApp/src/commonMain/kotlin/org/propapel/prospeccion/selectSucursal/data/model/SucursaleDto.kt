package org.propapel.prospeccion.selectSucursal.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SucursaleDto(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("direccion")
    val direccion: String,
    @SerialName("id")
    val id: Int,
    @SerialName("nombre")
    val nombre: String,
    @SerialName("updated_at")
    val updatedAt: String
)