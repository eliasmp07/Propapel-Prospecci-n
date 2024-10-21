package org.propapel.prospeccion.auth.domain

import org.propapel.prospeccion.core.domain.EmptyResult
import org.propapel.prospeccion.core.domain.utils.DataError

interface AuthRepository {
    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
}