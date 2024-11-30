package org.propapel.prospeccion.selectSucursal.data.repository

import io.ktor.client.HttpClient
import org.propapel.prospeccion.core.data.networking.get
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.selectSucursal.data.mappers.mapToDomain
import org.propapel.prospeccion.selectSucursal.data.model.UserResponse
import org.propapel.prospeccion.selectSucursal.domain.model.UserItem
import org.propapel.prospeccion.selectSucursal.domain.repository.UserRepository

class UserRepositoryImpl(
    private val httpClient: HttpClient
): UserRepository {
    override suspend fun getAllUsersBySucursal(sucursalId: Int): ResultExt<List<UserItem>, DataError.Network> {
       val result = httpClient.get<UserResponse>(
           route = "/users/getUserBySucursal/$sucursalId"
       )
        return when(result){
            is ResultExt.Error -> {
                ResultExt.Error(result.error)
            }
            is ResultExt.Success -> {
                ResultExt.Success(result.data.users.map {
                    it.mapToDomain()
                })
            }
        }
    }

}