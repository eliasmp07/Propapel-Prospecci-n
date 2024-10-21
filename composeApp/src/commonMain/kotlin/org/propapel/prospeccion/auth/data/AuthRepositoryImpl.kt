package org.propapel.prospeccion.auth.data

import io.ktor.client.HttpClient
import org.propapel.prospeccion.auth.domain.AuthRepository
import org.propapel.prospeccion.core.data.networking.post
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.domain.EmptyResult
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.SessionStorage
import org.propapel.prospeccion.core.domain.asEmptyDataResult
import org.propapel.prospeccion.core.domain.utils.DataError

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
): AuthRepository {
    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/auth/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )

        if (result is ResultExt.Success){
            //Se guarda la session del usuario en las preferencias del usuario
            sessionStorage.set(
                AuthInfo(
                    accessTokenExpirationTimestamp = result.data.accessTokenExpirationTimestamp,
                    image =  result.data.image?:"",
                    name = result.data.name,
                    isAdmin = result.data.isAdmin,
                    lastname = result.data.lastname,
                    email = result.data.email,
                    userId =  result.data.userId.toString(),
                    refreshToken =  result.data.refreshToken,
                    accessToken =  result.data.accessToken
                )
            )
        }

        return result.asEmptyDataResult()
    }
}