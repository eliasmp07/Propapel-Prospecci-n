package org.propapel.prospeccion.selectSucursal.domain.model

data class UserItem(
    val createdAt: String = "",
    val customers: List<CustomerUser> = emptyList(),
    val email: String = "",
    val id: Int = 0,
    val image: String = "",
    val lastname: String = "",
    val name: String = "",
    val phone: String = "",
    val puesto: String = "",
    val refreshToken: String = "",
    val roles: List<Role> = emptyList(),
    val sucursales: List<Sucursale> = emptyList(),
    val updatedAt: String = ""
)