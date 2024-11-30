package org.propapel.prospeccion.auth.domain

import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError

interface AuthRepository {
    suspend fun login(email: String, password: String): ResultExt<AuthInfo, DataError.Network>
}