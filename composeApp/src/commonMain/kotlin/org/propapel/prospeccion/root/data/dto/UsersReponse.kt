package org.propapel.prospeccion.root.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    val users: List<UserDto>
)
