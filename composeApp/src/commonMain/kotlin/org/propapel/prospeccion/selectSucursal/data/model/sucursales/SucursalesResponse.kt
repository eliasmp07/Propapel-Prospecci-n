package org.propapel.prospeccion.selectSucursal.data.model.sucursales

import kotlinx.serialization.Serializable
import org.propapel.prospeccion.selectSucursal.data.model.SucursaleDto

@Serializable
data class SucursalesResponse(
    val sucursales: List<SucursaleDto>
)
