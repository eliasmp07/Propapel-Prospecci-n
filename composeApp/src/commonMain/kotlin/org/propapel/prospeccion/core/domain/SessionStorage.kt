package org.propapel.prospeccion.core.domain

import kotlinx.coroutines.flow.Flow

interface SessionStorage {
    suspend fun set(user: AuthInfo?)
    suspend fun get(): AuthInfo?
    fun getUserFlow(): Flow<AuthInfo?>
}