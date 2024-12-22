package org.propapel.prospeccion.selectSucursal.data.repository

import io.ktor.client.HttpClient
import org.propapel.prospeccion.core.data.networking.get
import org.propapel.prospeccion.core.domain.ResultExt
import org.propapel.prospeccion.core.domain.utils.DataError
import org.propapel.prospeccion.selectSucursal.data.mappers.mapToDomain
import org.propapel.prospeccion.selectSucursal.data.model.SucursaleDto
import org.propapel.prospeccion.selectSucursal.data.model.sucursales.SucursalesResponse
import org.propapel.prospeccion.selectSucursal.domain.model.Sucursale
import org.propapel.prospeccion.selectSucursal.domain.repository.SucursalesRepository

class SucursalesRepositoryImpl(
    private val httpClient: HttpClient
): SucursalesRepository {
    override suspend fun getAllSucursales(): ResultExt<List<Sucursale>, DataError.Network> {
        val result = httpClient.get<SucursalesResponse>(
            route = "/sucursales/findAll"
        )

       return when(result){
            is ResultExt.Error -> {
                ResultExt.Error(result.error)
            }
            is ResultExt.Success -> {
                ResultExt.Success(result.data.sucursales.map {
                    it.mapToDomain()
                })
            }
        }
    }

    override suspend fun getSucursalById(id: Int): ResultExt<Sucursale, DataError.Network> {
        val result = httpClient.get<SucursaleDto>(
            route = "/sucursales/$id"
        )
        return when(result){
            is ResultExt.Error -> {
                ResultExt.Error(result.error)
            }
            is ResultExt.Success -> {
                ResultExt.Success(result.data.mapToDomain())
            }
        }
    }
}