package org.propapel.prospeccion.selectSucursal.domain.repository

import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.selectSucursal.domain.model.UserItem

interface UserRepository {
    suspend fun getAllUsersBySucursal(sucursalId: Int): ResultExt<List<UserItem>, DataError.Network>
}