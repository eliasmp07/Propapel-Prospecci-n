package org.propapel.prospeccion.selectSucursal.presentation.selectHome

import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.selectSucursal.domain.model.Sucursale

data class SelectSucursalState(
    val authInfo: AuthInfo = AuthInfo(),
    val sucusalId: Int = 0,
    val sucusales: List<Sucursale> = emptyList()
)
