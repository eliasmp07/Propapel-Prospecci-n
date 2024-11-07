package org.propapel.prospeccion.core.data.networking

import kotlinx.serialization.Serializable

@Serializable
data class AuthInfoSerializable(
    val accessToken: String,
    val refreshToken: String,
    val name: String,
    val isAdmin: Boolean,
    val lastname: String,
    val roles: List<String>,
    val email: String,
    val accessTokenExpirationTimestamp: Long,
    val userId: Int,
    val image: String?
)