package org.propapel.prospeccion.selectSucursal.domain.model

data class User(
    val createdAt: String,
    val email: String,
    val id: Int,
    val image: String,
    val lastname: String,
    val name: String,
    val password: String,
    val phone: String,
    val puesto: String,
    val refreshToken: String,
    val updatedAt: String
)