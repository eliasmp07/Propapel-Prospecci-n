package org.propapel.prospeccion.root.domain.repository

import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError

interface ProfileRepository {
    suspend fun updateProfile(name: String, lastname: String, image: String, phone: String):ResultExt<AuthInfo, DataError.Network>
}