package org.propapel.prospeccion.core.domain

data class AuthInfo(
    val accessToken: String = "",
    val refreshToken: String = "",
    val userId: String = "",
    val name: String = "",
    val roles: List<String> = emptyList(),
    val isAdmin: Boolean = false,
    val lastname: String = "",
    val email: String = "",
    val accessTokenExpirationTimestamp: Long = 0L,
    val image: String = ""
)