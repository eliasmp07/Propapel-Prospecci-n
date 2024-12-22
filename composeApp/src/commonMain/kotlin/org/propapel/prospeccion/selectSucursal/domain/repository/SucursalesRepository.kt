package org.propapel.prospeccion.selectSucursal.domain.repository

import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.selectSucursal.domain.model.Sucursale

interface SucursalesRepository {
    suspend fun getAllSucursales(): ResultExt<List<Sucursale>, DataError.Network>
    suspend fun getSucursalById(id: Int): ResultExt<Sucursale, DataError.Network>
}