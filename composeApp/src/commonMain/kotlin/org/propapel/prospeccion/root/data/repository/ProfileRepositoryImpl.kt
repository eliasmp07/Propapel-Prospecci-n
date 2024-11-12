package org.propapel.prospeccion.root.data.repository

import io.ktor.client.HttpClient
import org.propapel.prospeccion.core.data.networking.get
import org.propapel.prospeccion.core.data.networking.put
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.SessionStorage
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.root.data.dto.UpdateProfileRequest
import org.propapel.prospeccion.root.data.dto.UpdateProfileResponse
import org.propapel.prospeccion.root.data.dto.UsersResponse
import org.propapel.prospeccion.root.domain.repository.ProfileRepository
import org.propapel.prospeccion.root.domain.repository.User
import org.propapel.prospeccion.root.domain.repository.toUser

class ProfileRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
): ProfileRepository {

    override suspend fun updateProfile(name: String, lastname: String, image: String, phone: String): ResultExt<AuthInfo, DataError.Network> {

        val result = httpClient.put<UpdateProfileRequest, UpdateProfileResponse>(
            route = "/users/updateWithImage/${sessionStorage.get()?.userId?:""}",
            body = UpdateProfileRequest(
                name = name,
                lastname = lastname,
                image = image,
                phone = phone
            )
        )
        return when(result){
            is ResultExt.Error -> {
                ResultExt.Error(result.error)
            }
            is ResultExt.Success -> {
                sessionStorage.set(
                    AuthInfo(
                        accessTokenExpirationTimestamp = sessionStorage.get()?.accessTokenExpirationTimestamp?:0,
                        image =  result.data.image,
                        name = result.data.name,
                        lastname = result.data.lastname,
                        email = sessionStorage.get()?.email?:"",
                        userId =  sessionStorage.get()?.userId?:"",
                        refreshToken =  sessionStorage.get()?.refreshToken?:"",
                        accessToken = sessionStorage.get()?.accessToken?:"",
                    )
                )
                ResultExt.Success(
                    AuthInfo(
                        accessTokenExpirationTimestamp = sessionStorage.get()?.accessTokenExpirationTimestamp?:0,
                        image =  result.data.image,
                        name = result.data.name,
                        lastname = result.data.lastname,
                        email = sessionStorage.get()?.email?:"",
                        userId =  sessionStorage.get()?.userId?:"",
                        refreshToken =  sessionStorage.get()?.refreshToken?:"",
                        accessToken = sessionStorage.get()?.accessToken?:"",
                    )
                )
            }
        }
    }

    override suspend fun getAllUsers(): ResultExt<List<User>, DataError.Network> {
        val result = httpClient.get<UsersResponse>(
            route = "/users/getAllUsers"
        )

        return when(result){
            is ResultExt.Error -> {
                ResultExt.Error(result.error)
            }
            is ResultExt.Success -> {
                ResultExt.Success(result.data.users.map {
                    it.toUser()
                })
            }
        }
    }
}