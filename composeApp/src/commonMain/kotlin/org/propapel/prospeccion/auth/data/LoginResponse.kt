package org.propapel.prospeccion.auth.data
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpirationTimestamp: Long,
    val userId: Int,
    val isAdmin: Boolean,
    val name: String,
    val lastname: String,
    val email: String,
    val image: String?
)