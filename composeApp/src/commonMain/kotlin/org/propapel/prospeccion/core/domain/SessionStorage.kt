package org.propapel.prospeccion.core.domain

interface SessionStorage {
    suspend fun set(user: AuthInfo?)
    suspend fun get(): AuthInfo?
}