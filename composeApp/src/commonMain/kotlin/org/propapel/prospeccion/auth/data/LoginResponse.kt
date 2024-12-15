package org.propapel.prospeccion.auth.data
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val puesto: String,
    val accessTokenExpirationTimestamp: Long,
    val userId: Int,
    val roles: List<String>?,
    val sucursales: List<String>?,
    val name: String,
    val lastname: String,
    val email: String,
    val image: String?
)