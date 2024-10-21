package org.propapel.prospeccion.root.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val lastname: String,
    val name: String,
    val phone: String?,
    val image: String?,
    val isAdmin: Boolean

)