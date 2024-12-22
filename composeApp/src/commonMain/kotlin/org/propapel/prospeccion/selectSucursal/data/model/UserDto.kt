package org.propapel.prospeccion.selectSucursal.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("email")
    val email: String,
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String?,
    @SerialName("lastname")
    val lastname: String,
    @SerialName("name")
    val name: String,
    @SerialName("password")
    val password: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("puesto")
    val puesto: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("updated_at")
    val updatedAt: String
)
