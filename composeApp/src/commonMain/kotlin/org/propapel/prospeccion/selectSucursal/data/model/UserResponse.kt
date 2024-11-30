package org.propapel.prospeccion.selectSucursal.data.model


import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val users: List<UserResponseItem>
)