package org.propapel.prospeccion.selectSucursal.presentation.selectHome

import org.propapel.prospeccion.core.domain.AuthInfo

data class SelectSucursalState(
    val authInfo: AuthInfo = AuthInfo(),
    val sucusalId: Int = 0,
    val sucusales: List<Sucusales> = provideFakeSucusales()
)

data class Sucusales(
    val id: Int,
    val sucursal: String
)

fun provideFakeSucusales(): List<Sucusales>{
    return listOf(
        Sucusales(
            id = 1,
            sucursal = "Propapel Merida"
        ),
        Sucusales(
            id = 2,
            sucursal = "Propapel Monterrey"
        ),
        Sucusales(
            id = 3,
            sucursal = "Propapel Mexico"
        )
    )
}